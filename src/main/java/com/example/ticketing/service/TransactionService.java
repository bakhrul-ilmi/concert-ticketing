package com.example.ticketing.service;

import com.example.ticketing.exception.NotFoundException;
import com.example.ticketing.exception.UnprocessableEntityException;
import com.example.ticketing.helper.ErrorCodesEnum;
import com.example.ticketing.model.*;
import com.example.ticketing.model.enumeration.QueueStatus;
import com.example.ticketing.model.enumeration.TicketStatus;
import com.example.ticketing.model.enumeration.TransactionStatus;
import com.example.ticketing.model.enumeration.UserTicketStatus;
import com.example.ticketing.repository.EventTransactionRepository;
import com.example.ticketing.repository.UserTicketRepository;
import com.example.ticketing.util.DateTimeUtil;
import com.example.ticketing.repository.TicketRepository;
import com.example.ticketing.repository.EventQueueRepository;
import com.example.ticketing.repository.EventRepository;
import com.example.ticketing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionService {
    private final EventTransactionRepository eventTransactionRepository;
    private final UserTicketRepository userTicketRepository;
    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventQueueRepository eventQueueRepository;
    private final QueueService queueService;


    @Autowired
    public TransactionService(EventTransactionRepository eventTransactionRepository, UserTicketRepository userTicketRepository, TicketRepository ticketRepository, EventRepository eventRepository, UserRepository userRepository, EventQueueRepository eventQueueRepository, QueueService queueService) {
        this.eventTransactionRepository = eventTransactionRepository;
        this.userTicketRepository = userTicketRepository;
        this.ticketRepository = ticketRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.eventQueueRepository = eventQueueRepository;
        this.queueService = queueService;
    }

    @Transactional
    public EventTransaction CreateTransaction(Integer eventId, Integer userId, List<String> seatIds) {
        EventQueue eventQueue = eventQueueRepository.findFirstByEventIdAndUserIdOrderByIdDesc(eventId, userId)
                .orElseThrow(() -> new NotFoundException(ErrorCodesEnum.SYSTEM_ERROR));

        if (eventQueue.getStatus() != QueueStatus.PROCESS) {
            throw new UnprocessableEntityException(ErrorCodesEnum.TICKET_NOT_AVAILABLE);
        }

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(ErrorCodesEnum.SYSTEM_ERROR));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCodesEnum.SYSTEM_ERROR));
        List<Ticket> tickets = ticketRepository.findAllAndLockForUpdateByEventIdAndStatusAndSeatIdIn(eventId, TicketStatus.AVAILABLE, seatIds);
        if (tickets.size() != seatIds.size()) {
            throw new NotFoundException(ErrorCodesEnum.TICKET_NOT_AVAILABLE);
        }

        Double totalPrice = event.getPrice() * seatIds.size();
        EventTransaction transaction = EventTransaction.builder()
                .event(event)
                .user(user)
                .price(totalPrice)
                .priceCurrency(event.getPriceCurrency())
                .status(TransactionStatus.PENDING)
                .createdAt(DateTimeUtil.getCurrentDateTime())
                .expiredAt(DateTimeUtil.getCurrentDateTime().plusMinutes(10))
                .build();   
        transaction = eventTransactionRepository.save(transaction);

        // Dummy payment transaction id for update
        String paymentTransactionId = "INV-" + transaction.getId();
        transaction.setPaymentTransactionId(paymentTransactionId);
        transaction = eventTransactionRepository.save(transaction);

        buildUserTicket(transaction, user, tickets);

        queueService.ReleaseQueue(eventId, eventQueue.getQueueNumber());

        return transaction;
    }

    public void buildUserTicket(EventTransaction transaction, User user, List<Ticket> tickets) {
        for (Ticket ticket : tickets) {
            UserTicket userTicket = UserTicket.builder()
                .transaction(transaction)
                .user(user)
                .ticket(ticket)
                .status(UserTicketStatus.PENDING)
                .bookedAt(DateTimeUtil.getCurrentDateTime())
                .bookingExpiredAt(DateTimeUtil.getCurrentDateTime().plusMinutes(10))
                .build();

            userTicketRepository.save(userTicket);

            ticket.setStatus(TicketStatus.BOOKED);
            ticket.setUpdatedAt(DateTimeUtil.getCurrentDateTime());
            ticketRepository.save(ticket);
        }
    }

    public EventTransaction GetLatestTransactionEventUser(Integer eventId, Integer userId) {
        return eventTransactionRepository.findLatestByEventIdAndUserId(eventId, userId).orElseThrow(() -> new NotFoundException(ErrorCodesEnum.SYSTEM_ERROR));
    }

    public EventTransaction GetTransactionById(Integer transactionId) {
        return eventTransactionRepository.findById(transactionId).orElseThrow(() -> new NotFoundException(ErrorCodesEnum.SYSTEM_ERROR));
    }

    public List<EventTransaction> GetTransactionsByUserId(Integer userId) {
        return eventTransactionRepository.findAllByUserId(userId);
    }
    
    @Transactional
    public void UpdateTransaction(String paymentTransactionId, TransactionStatus status) {
        EventTransaction transaction = eventTransactionRepository.findByPaymentTransactionId(paymentTransactionId).orElseThrow(() -> new NotFoundException(ErrorCodesEnum.SYSTEM_ERROR));
        
        if (transaction.getStatus() != TransactionStatus.PENDING) {
            throw new UnprocessableEntityException(ErrorCodesEnum.TRANSACTION_NOT_PENDING);
        }
        List<UserTicket> userTickets = userTicketRepository.findAllByTransactionId(transaction.getId());

        if (status == TransactionStatus.PAID) {
            transaction.setPaidAt(DateTimeUtil.getCurrentDateTime());
            for (UserTicket userTicket : userTickets) {
                userTicket.setStatus(UserTicketStatus.ISSUED);
                userTicket.setIssuedAt(DateTimeUtil.getCurrentDateTime());
                userTicketRepository.save(userTicket);

                Ticket ticket = ticketRepository.findById(userTicket.getTicket().getId()).orElseThrow(() -> new NotFoundException(ErrorCodesEnum.SYSTEM_ERROR));
                ticket.setStatus(TicketStatus.ISSUED);
                ticket.setUpdatedAt(DateTimeUtil.getCurrentDateTime());
                ticketRepository.save(ticket);
            }
        }

        if (status == TransactionStatus.EXPIRED) {
            for (UserTicket userTicket : userTickets) {
                userTicket.setStatus(UserTicketStatus.CANCELLED);
                userTicketRepository.save(userTicket);

                Ticket ticket = ticketRepository.findById(userTicket.getTicket().getId()).orElseThrow(() -> new NotFoundException(ErrorCodesEnum.SYSTEM_ERROR));
                ticket.setStatus(TicketStatus.AVAILABLE);
                ticket.setUpdatedAt(DateTimeUtil.getCurrentDateTime());
                ticketRepository.save(ticket);
            }
            transaction.setExpiredAt(DateTimeUtil.getCurrentDateTime());
        }

        transaction.setStatus(status);
        eventTransactionRepository.save(transaction);
    }
}

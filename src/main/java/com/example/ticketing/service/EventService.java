package com.example.ticketing.service;

import com.example.ticketing.model.Event;
import com.example.ticketing.model.Ticket;
import com.example.ticketing.model.enumeration.TicketStatus;
import com.example.ticketing.repository.EventRepository;
import com.example.ticketing.repository.EventQueueRepository;
import com.example.ticketing.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.RedisClient;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final TicketRepository ticketRepository;

    @Autowired
    public EventService(EventRepository eventRepository, TicketRepository ticketRepository) {
        this.eventRepository = eventRepository;
        this.ticketRepository = ticketRepository;
    }

    public List<Event> GetActiveEvents(String searchKey, LocalDateTime time) {
        System.out.println(searchKey);
        System.out.println(time);
        return eventRepository.searchActiveEvent(searchKey, time);
    }

    public List<Ticket> GetTickets(Integer eventId) {
        return ticketRepository.findAllByEventIdAndStatusOrderByIdAsc(eventId, TicketStatus.AVAILABLE);
    }
}

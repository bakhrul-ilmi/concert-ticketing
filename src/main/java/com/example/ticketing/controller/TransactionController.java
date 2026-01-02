package com.example.ticketing.controller;

import com.example.ticketing.model.EventTransaction;
import com.example.ticketing.pojo.request.PurchaseTicketRequest;
import com.example.ticketing.pojo.request.UpdateTransactionRequest;
import com.example.ticketing.pojo.response.EventTransactionResponse;
import com.example.ticketing.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;
import com.example.ticketing.model.enumeration.TransactionStatus;

@RequestMapping("/transaction")
@RestController
public class TransactionController {
    private final TransactionService transactionService;
    
    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping(value = "/purchase")
    public ResponseEntity<EventTransactionResponse> createTransaction(@RequestBody PurchaseTicketRequest req) {
        List<String> seatIds = Arrays.asList(req.getSeatIdsString().split(","));
        EventTransaction transaction = transactionService.CreateTransaction(req.getEventId(), req.getUserId(), seatIds);
        return ResponseEntity.ok(toEventTransactionResponse(transaction));
    }

    @PostMapping(value = "/update")
    public ResponseEntity<EventTransactionResponse> updateTransaction(@RequestBody UpdateTransactionRequest req) {
        transactionService.UpdateTransaction(req.getPaymentTransactionId(), req.getStatus());
        return ResponseEntity.ok(null);
    }

    @GetMapping(value = "/detail")
    public ResponseEntity<EventTransactionResponse> getTransaction(@RequestParam Integer transactionId) {
        EventTransaction transaction = transactionService.GetTransactionById(transactionId);
        return ResponseEntity.ok(toEventTransactionResponse(transaction));
    }

    @GetMapping(value = "/list")
    public ResponseEntity<List<EventTransactionResponse>> getTransactionsByUserId(@RequestParam Integer userId) {
        List<EventTransaction> transactions = transactionService.GetTransactionsByUserId(userId);
        List<EventTransactionResponse> responses = transactions.stream()
                .map(this::toEventTransactionResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    private EventTransactionResponse toEventTransactionResponse(EventTransaction transaction) {
        return EventTransactionResponse.builder()
                .id(transaction.getId())
                .eventId(transaction.getEvent() != null ? transaction.getEvent().getId() : null)
                .userId(transaction.getUser() != null ? transaction.getUser().getId() : null)
                .status(transaction.getStatus())
                .price(transaction.getPrice())
                .priceCurrency(transaction.getPriceCurrency())
                .paymentTransactionId(transaction.getPaymentTransactionId())
                .createdAt(transaction.getCreatedAt())
                .updatedAt(transaction.getUpdatedAt())
                .expiredAt(transaction.getExpiredAt())
                .paidAt(transaction.getPaidAt())
                .build();
    }
}

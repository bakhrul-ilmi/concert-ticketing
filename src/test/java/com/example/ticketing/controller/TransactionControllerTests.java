package com.example.ticketing.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.ticketing.mock.model.EventQueueMock;
import com.example.ticketing.mock.model.TransactionMock;
import com.example.ticketing.model.enumeration.QueueStatus;
import com.example.ticketing.pojo.request.CheckUserQueueRequest;
import com.example.ticketing.pojo.request.PurchaseTicketRequest;
import com.example.ticketing.pojo.request.RegisterQueueRequest;
import com.example.ticketing.pojo.response.EventQueueResponse;

import com.example.ticketing.pojo.response.EventTransactionResponse;
import com.example.ticketing.service.TransactionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.ticketing.service.QueueService;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerTests {
    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private TransactionService transactionService;

    @Test
    @DisplayName("Positive Case: Purchase Ticket")
    public void positivePurchaseTicket() {
        when(transactionService.CreateTransaction(any(), any(), any())).thenReturn(TransactionMock.getInstance().buildTransaction());
        PurchaseTicketRequest req = new PurchaseTicketRequest();
        req.setUserId(1);
        req.setEventId(2);
        req.setSeatIdsString("1A");
        ResponseEntity<EventTransactionResponse> res = transactionController.createTransaction(req);
        assertEquals(res.getStatusCode(), HttpStatus.OK);
    }
}
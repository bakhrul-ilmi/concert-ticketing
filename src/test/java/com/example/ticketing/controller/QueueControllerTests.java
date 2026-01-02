package com.example.ticketing.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.ticketing.mock.model.EventQueueMock;
import com.example.ticketing.model.enumeration.QueueStatus;
import com.example.ticketing.pojo.request.CheckUserQueueRequest;
import com.example.ticketing.pojo.request.RegisterQueueRequest;
import com.example.ticketing.pojo.response.EventQueueResponse;

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
public class QueueControllerTests {

    @InjectMocks
    private QueueController queueController;

    @Mock
    private QueueService queueService;

    @Test
    @DisplayName("Positive Case: register queue")
    public void positiveRegisterQueue() {
        
        when(queueService.RegisterUserQueue(any(), any())).thenReturn(EventQueueMock.getInstance().buildEventQueue(QueueStatus.WAITING));
        RegisterQueueRequest req = new RegisterQueueRequest();
        req.setEventId(1);
        req.setPhoneNumber("0812312321");
        ResponseEntity<EventQueueResponse> res = queueController.registerQueue(req);
        assertEquals(res.getStatusCode(), HttpStatus.OK);
    }

    @Test
    @DisplayName("Positive Case: check user queue")
    public void positiveCheckUserQueue() {
        
        when(queueService.CheckUserQueue(any(), any())).thenReturn(EventQueueMock.getInstance().buildEventQueue(QueueStatus.WAITING));
        CheckUserQueueRequest req = new CheckUserQueueRequest();
        req.setUserId(1);
        req.setEventId(1);
        ResponseEntity<EventQueueResponse> res = queueController.checkQueue(req);
        assertEquals(res.getStatusCode(), HttpStatus.OK);
    }
}

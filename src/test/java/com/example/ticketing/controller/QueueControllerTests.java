package com.example.ticketing.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.ticketing.mock.model.EventQueueMock;
import com.example.ticketing.model.enumeration.QueueStatus;
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
    public void positifRegisterQueue() {
        
        when(queueService.RegisterUserQueue(any(), any())).thenReturn(EventQueueMock.getInstance().buildEventQueue(QueueStatus.WAITING));

        ResponseEntity<EventQueueResponse> res = queueController.registerQueue(any(), any());
        assertEquals(res.getStatusCode(), HttpStatus.OK);
    }
}

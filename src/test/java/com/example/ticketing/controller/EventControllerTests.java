package com.example.ticketing.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import com.example.ticketing.mock.model.EventMock;
import com.example.ticketing.mock.model.EventQueueMock;
import com.example.ticketing.mock.model.TicketMock;
import com.example.ticketing.model.enumeration.QueueStatus;
import com.example.ticketing.pojo.response.EventQueueResponse;
import com.example.ticketing.pojo.response.EventResponse;
import com.example.ticketing.pojo.response.TicketResponse;
import com.example.ticketing.service.EventService;
import com.example.ticketing.util.DateTimeUtil;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class EventControllerTests {
    
    @InjectMocks
    private EventController eventController;

    @Mock
    private EventService eventService;

    @Test
    @DisplayName("Positive Case: search event")
    public void positiveSearchEvent(){
        when(eventService.GetActiveEvents(any(), any())).thenReturn(EventMock.getInstance().buildListEvent());
        ResponseEntity<List<EventResponse>> res = eventController.getActiveEvents("test");
        assertEquals(res.getStatusCode(), HttpStatus.OK);
    }

    @Test
    @DisplayName("Positive Case: get list ticket")
    public void positiveGetTickets(){
        when(eventService.GetTickets(any())).thenReturn(TicketMock.getInstance().buildListTicket());
        ResponseEntity<List<TicketResponse>> res = eventController.getTickets(1);
        assertEquals(res.getStatusCode(), HttpStatus.OK);
    }
}

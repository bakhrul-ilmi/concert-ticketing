package com.example.ticketing.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.example.ticketing.util.DateTimeUtil;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import com.example.ticketing.model.Event;
import com.example.ticketing.model.Ticket;
import com.example.ticketing.pojo.response.EventResponse;
import com.example.ticketing.pojo.response.TicketResponse;
import com.example.ticketing.service.EventService;

@RequestMapping("/event")
@RestController
public class EventController {
    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping(value = "/list")
    public ResponseEntity<List<EventResponse>> getActiveEvents(@RequestParam String searchKey) {
        LocalDateTime time = DateTimeUtil.getCurrentDateTime();
        List<Event> events = eventService.GetActiveEvents(searchKey, time);
        List<EventResponse> responses = events.stream()
                .map(this::toEventResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping(value = "/tickets")
    public ResponseEntity<List<TicketResponse>> getTickets(@RequestParam Integer eventId) {
        List<Ticket> tickets = eventService.GetTickets(eventId);
        List<TicketResponse> responses = tickets.stream()
                .map(this::toTicketResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    private EventResponse toEventResponse(Event event) {
        return EventResponse.builder()
                .id(event.getId())
                .name(event.getName())
                .totalCapacity(event.getTotalCapacity())
                .price(event.getPrice())
                .priceCurrency(event.getPriceCurrency())
                .seatType(event.getSeatType())
                .buytimeStart(event.getBuytimeStart())
                .buytimeEnd(event.getBuytimeEnd())
                .createdAt(event.getCreatedAt())
                .updatedAt(event.getUpdatedAt())
                .build();
    }

    private TicketResponse toTicketResponse(Ticket ticket) {
        return TicketResponse.builder()
                .id(ticket.getId())
                .eventId(ticket.getEvent() != null ? ticket.getEvent().getId() : null)
                .ticketKey(ticket.getTicketKey())
                .seatId(ticket.getSeatId())
                .status(ticket.getStatus())
                .createdAt(ticket.getCreatedAt())
                .updatedAt(ticket.getUpdatedAt())
                .build();
    }
}

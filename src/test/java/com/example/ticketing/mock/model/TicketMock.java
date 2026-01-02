package com.example.ticketing.mock.model;

import com.example.ticketing.model.Event;
import com.example.ticketing.model.Ticket;
import com.example.ticketing.model.enumeration.TicketStatus;

import java.util.ArrayList;
import java.util.List;

public class TicketMock {
    private static TicketMock ins = null;

    public static TicketMock getInstance() {
        if (ins == null) {
            ins = new TicketMock();
        }

        return ins;
    }

    public List<Ticket> buildListTicket() {
        return new ArrayList<Ticket>(){{ add(TicketMock.this.buildTicket()); }};
    }

    public Ticket buildTicket(){
        Event event = Event.builder().id(1).build();
        return Ticket.builder()
                .id(1)
                .event(event)
                .ticketKey("1")
                .status(TicketStatus.AVAILABLE)
                .seatId("1A")
                .build();
    }
}

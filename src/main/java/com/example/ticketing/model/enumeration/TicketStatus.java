package com.example.ticketing.model.enumeration;
import lombok.Getter;

public enum TicketStatus {
    AVAILABLE(0, "available"),
    BOOKED(1, "booked"),
    ISSUED(2, "issued");

    @Getter
    private final int id;

    @Getter
    private final String type;

    TicketStatus(int id, String type) {
        this.id = id;
        this.type = type;
    }
}

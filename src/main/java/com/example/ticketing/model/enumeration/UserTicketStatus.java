package com.example.ticketing.model.enumeration;
import lombok.Getter;

public enum UserTicketStatus {
    PENDING(0, "pending"),
    ISSUED(1, "issued"),
    CANCELLED(2, "cancelled");

    @Getter
    private final int id;

    @Getter
    private final String type;

    UserTicketStatus(int id, String type) {
        this.id = id;
        this.type = type;
    }
}

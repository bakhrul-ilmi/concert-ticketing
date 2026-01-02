package com.example.ticketing.model.enumeration;
import lombok.Getter;

public enum TransactionStatus {
    PENDING(0, "pending"),
    PAID(1, "paid"),
    COMPLETED(2, "completed"),
    EXPIRED(3, "expired");


    @Getter
    private final int id;

    @Getter
    private final String type;

    TransactionStatus(int id, String type) {
        this.id = id;
        this.type = type;
    }
}

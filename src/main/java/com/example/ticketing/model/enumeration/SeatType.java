package com.example.ticketing.model.enumeration;
import lombok.Getter;

public enum SeatType {
    FREE_SEAT(0, "free_seat"),
    WITH_SEAT(1, "with_seat");

    @Getter
    private final int id;

    @Getter
    private final String type;

    SeatType(int id, String type) {
        this.id = id;
        this.type = type;
    }
}

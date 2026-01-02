package com.example.ticketing.model.enumeration;
import lombok.Getter;

public enum QueueStatus {
    WAITING(0, "waiting"),
    PROCESS(1, "process"),
    DONE(2, "done"),
    EXPIRED(3,"expired");

    @Getter
    private final int id;

    @Getter
    private final String type;

    QueueStatus(int id, String type) {
        this.id = id;
        this.type = type;
    }
}

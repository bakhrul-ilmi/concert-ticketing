package com.example.ticketing.mock.model;

import com.example.ticketing.model.EventQueue;
import com.example.ticketing.model.enumeration.QueueStatus;
import com.example.ticketing.util.DateTimeUtil;

public class EventQueueMock {
    private static EventQueueMock ins = null;

    public static EventQueueMock getInstance() {
        if (ins == null) {
            ins = new EventQueueMock();
        }

        return ins;
    }

    public EventQueue buildEventQueue(QueueStatus status) {
        return EventQueue.builder()
            .id(1)
            .eventId(1)
            .userId(1)
            .queueNumber(1)
            .queueKey("queue-1")
            .status(status)
            .buyExpiredAt(DateTimeUtil.getCurrentDateTime().plusMinutes(10))
            .createdAt(DateTimeUtil.getCurrentDateTime())
            .updatedAt(DateTimeUtil.getCurrentDateTime())
            .build();
    }
}

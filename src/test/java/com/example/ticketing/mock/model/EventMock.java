package com.example.ticketing.mock.model;

import com.example.ticketing.model.Event;
import com.example.ticketing.model.enumeration.QueueStatus;
import com.example.ticketing.util.DateTimeUtil;

import java.util.ArrayList;
import java.util.List;

public class EventMock {
    private static EventMock ins = null;

    public static EventMock getInstance() {
        if (ins == null) {
            ins = new EventMock();
        }

        return ins;
    }

    public List<Event> buildListEvent() {
        return new ArrayList<Event>(){{ add(EventMock.this.buildEvent()); }};
    }

    public Event buildEvent() {
        return Event.builder()
            .id(1)
            .name("testing")
            .buytimeEnd(DateTimeUtil.getCurrentDateTime().plusDays(1))
            .buytimeStart(DateTimeUtil.getCurrentDateTime())
            .createdAt(DateTimeUtil.getCurrentDateTime())
            .updatedAt(DateTimeUtil.getCurrentDateTime())
            .build();
    }
}

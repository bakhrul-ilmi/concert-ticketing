package com.example.ticketing.mock.model;

import com.example.ticketing.model.Event;
import com.example.ticketing.model.EventTransaction;
import com.example.ticketing.util.DateTimeUtil;

import java.util.ArrayList;
import java.util.List;

public class TransactionMock {
    private static TransactionMock ins = null;

    public static TransactionMock getInstance() {
        if (ins == null) {
            ins = new TransactionMock();
        }

        return ins;
    }

    public List<EventTransaction> buildListTransactions() {
        return new ArrayList<EventTransaction>(){{ add(TransactionMock.this.buildTransaction()); }};
    }

    public EventTransaction buildTransaction() {
        Event event = Event.builder().id(1).build();
        return EventTransaction.builder()
                .id(1)
                .price(10000.00)
                .paymentTransactionId("testing")
                .event(event)
                .createdAt(DateTimeUtil.getCurrentDateTime())
                .updatedAt(DateTimeUtil.getCurrentDateTime())
                .build();
    }
}

package com.example.ticketing.repository;

import com.example.ticketing.model.EventTransaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventTransactionRepository extends CrudRepository<EventTransaction, Integer> {
    Optional<EventTransaction> findLatestByEventIdAndUserId(Integer eventId, Integer userId);

    List<EventTransaction> findAllByUserId(Integer userId);

    Optional<EventTransaction> findByPaymentTransactionId(String paymentTransactionId);
}

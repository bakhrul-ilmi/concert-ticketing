package com.example.ticketing.repository;

import com.example.ticketing.model.EventQueue;
import com.example.ticketing.model.enumeration.QueueStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventQueueRepository extends CrudRepository<EventQueue, Integer> {
    Optional<EventQueue> findLastByEventIdAndUserIdAndStatus(Integer eventId, Integer userId, QueueStatus status);
    Optional<EventQueue> findFirstByEventIdAndUserIdOrderByIdDesc(Integer eventId, Integer userId);

    Optional<EventQueue> findByEventIdAndQueueNumber(Integer eventId, Integer queueNumber);

}

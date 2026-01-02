package com.example.ticketing.repository;

import com.example.ticketing.model.Event;
import com.example.ticketing.model.Ticket;
import com.example.ticketing.model.enumeration.TicketStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import jakarta.persistence.LockModeType;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Integer> {
    List<Ticket> findAllByEventIdAndStatusOrderByIdAsc(Integer eventId, TicketStatus status) ;

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Ticket> findAllAndLockForUpdateByEventIdAndStatusAndSeatIdIn(Integer eventId, TicketStatus status, List<String> seatIds);
}

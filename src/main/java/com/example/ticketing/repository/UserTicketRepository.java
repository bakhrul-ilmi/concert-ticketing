package com.example.ticketing.repository;

import com.example.ticketing.model.UserTicket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTicketRepository extends CrudRepository<UserTicket, Integer> {
    Optional<UserTicket> findByTransactionId(Integer TransactionId);

    List<UserTicket> findAllByTransactionId(Integer transactionId);
}

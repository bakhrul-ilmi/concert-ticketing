package com.example.ticketing.repository;

import com.example.ticketing.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByPhoneNumber(String phoneNumber);
}

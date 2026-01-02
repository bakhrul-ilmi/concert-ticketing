package com.example.ticketing.repository;

import com.example.ticketing.model.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.query.Param;

@Repository
public interface EventRepository extends CrudRepository<Event, Integer> {

    @Query("SELECT e from Event e where e.name LIKE '%' || :searchKey || '%' AND e.buytimeStart < :time AND e.buytimeEnd > :time order by id asc")
    List<Event> searchActiveEvent(@Param("searchKey") String searchKey, @Param("time") LocalDateTime time);
}
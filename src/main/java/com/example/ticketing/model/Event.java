package com.example.ticketing.model;

import java.time.LocalDateTime;

import com.example.ticketing.model.enumeration.SeatType;
import lombok.*;
import jakarta.persistence.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "total_capacity")
    private Integer totalCapacity;

    @Column(name = "price")
    private Double price;

    @Column(name = "price_currency")
    private String priceCurrency;

    @Column(name = "seat_type")
    private SeatType seatType;

    @Column(name = "buytime_start")
    private LocalDateTime buytimeStart;

    @Column(name = "buytime_end")
    private LocalDateTime buytimeEnd;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

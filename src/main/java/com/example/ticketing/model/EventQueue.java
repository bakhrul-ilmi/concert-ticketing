package com.example.ticketing.model;

import java.time.LocalDateTime;

import com.example.ticketing.model.enumeration.QueueStatus;
import lombok.*;
import jakarta.persistence.*;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "event_queue")
public class EventQueue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "event_id", nullable = false)
    private Integer eventId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "queue_number")
    private Integer queueNumber;

    @Column(name = "queue_key")
    private String queueKey;

    @Column(name = "status")
    private QueueStatus status;

    @Column(name = "buy_expired_at")
    private LocalDateTime buyExpiredAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

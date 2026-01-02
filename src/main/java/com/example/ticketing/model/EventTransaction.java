package com.example.ticketing.model;

import java.time.LocalDateTime;

import com.example.ticketing.model.enumeration.TransactionStatus;
import lombok.*;
import jakarta.persistence.*;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "event_transaction")
public class EventTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "status")
    private TransactionStatus status;

    @Column(name = "price")
    private Double price;

    @Column(name = "price_currency")
    private String priceCurrency;

    @Column(name = "payment_transaction_id")
    private String paymentTransactionId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "expired_at")
    private LocalDateTime expiredAt;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;
}
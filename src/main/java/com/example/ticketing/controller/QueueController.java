package com.example.ticketing.controller;

import com.example.ticketing.model.EventQueue;
import com.example.ticketing.pojo.request.CheckUserQueueRequest;
import com.example.ticketing.pojo.request.RegisterQueueRequest;
import com.example.ticketing.pojo.response.EventQueueResponse;
import com.example.ticketing.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RequestMapping("/queue")
@RestController
public class QueueController {
    private final QueueService queueService;

    @Autowired
    public QueueController(QueueService queueService) {
        this.queueService = queueService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<EventQueueResponse> registerQueue(@RequestBody RegisterQueueRequest req) {
        EventQueue queue = queueService.RegisterUserQueue(req.getPhoneNumber(), req.getEventId());
        return ResponseEntity.ok(toEventQueueResponse(queue));
    }

    @PostMapping("/check")
    public ResponseEntity<EventQueueResponse> checkQueue(@RequestBody CheckUserQueueRequest req) {
        EventQueue queue = queueService.CheckUserQueue(req.getEventId(), req.getUserId());
        return ResponseEntity.ok(toEventQueueResponse(queue));
    }

    private EventQueueResponse toEventQueueResponse(EventQueue queue) {
        return EventQueueResponse.builder()
                .id(queue.getId())
                .eventId(queue.getEventId())
                .userId(queue.getUserId())
                .queueNumber(queue.getQueueNumber())
                .queueKey(queue.getQueueKey())
                .status(queue.getStatus())
                .buyExpiredAt(queue.getBuyExpiredAt())
                .createdAt(queue.getCreatedAt())
                .updatedAt(queue.getUpdatedAt())
                .build();
    }
}

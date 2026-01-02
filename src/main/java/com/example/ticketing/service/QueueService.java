package com.example.ticketing.service;

import com.example.ticketing.exception.InternalServerErrorException;
import com.example.ticketing.exception.NotFoundException;
import com.example.ticketing.helper.ErrorCodesEnum;
import com.example.ticketing.model.*;
import com.example.ticketing.model.enumeration.QueueStatus;
import com.example.ticketing.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.ticketing.util.DateTimeUtil;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.RedisClient;
import redis.clients.jedis.params.HSetExParams;

import javax.swing.text.html.Option;
import java.util.*;

@Slf4j
@Service
public class QueueService {
    private static final String QUEUE_REDIS_PATTERN_KEY = "queue-event-%s";
    public static final String TRX_QUEUE_REDIS_PATTERN_KEY = "trx-queue-event-%s";
    private static final Integer MAX_QUEUE_TRANSACTION_NUMBER = 2;
    private static final Integer EXPIRED_TIME_QUEUE_TRANSACTION = 1000;

    private final EventRepository eventRepository;
    private final EventQueueRepository eventQueueRepository;
    private final UserRepository userRepository;
    private final RedisService redisService;

    @Autowired
    public QueueService(EventRepository eventRepository,
                        EventQueueRepository eventQueueRepository,
                        UserRepository userRepository,
                        RedisService redisService) {
        this.eventRepository = eventRepository;
        this.eventQueueRepository = eventQueueRepository;
        this.userRepository = userRepository;
        this.redisService = redisService;
    }

    @Transactional
    public EventQueue RegisterUserQueue(String phoneNumber, Integer eventId) {
        Optional<User> optUser = userRepository.findByPhoneNumber(phoneNumber);
        if (optUser.isEmpty()) {
            User user = User.builder()
                    .phoneNumber(phoneNumber)
                    .createdAt(DateTimeUtil.getCurrentDateTime())
                    .build();

            user = userRepository.save(user);
            optUser = Optional.of(user);
        }

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(ErrorCodesEnum.SYSTEM_ERROR));

        Optional<EventQueue> eventQueue = eventQueueRepository.findFirstByEventIdAndUserIdOrderByIdDesc(event.getId(), optUser.get().getId());
        if (eventQueue.isPresent()){
            if(eventQueue.get().getStatus() != QueueStatus.EXPIRED) {
                CheckUserQueue(event.getId(), optUser.get().getId());
                return eventQueue.get();
            }
        }

        String redisKey = String.format(QUEUE_REDIS_PATTERN_KEY, event.getId());
        Long queueNumber = redisService.incr(redisKey);

        EventQueue queue = EventQueue.builder()
                .queueNumber(queueNumber.intValue())
                .eventId(event.getId())
                .userId(optUser.get().getId())
                .status(QueueStatus.WAITING)
                .createdAt(DateTimeUtil.getCurrentDateTime())
                .build();

        queue = eventQueueRepository.save(queue);

        return queue;
    }

    public EventQueue CheckUserQueue(Integer eventId, Integer userId){
        EventQueue queue = eventQueueRepository.findFirstByEventIdAndUserIdOrderByIdDesc(eventId, userId)
                .orElseThrow(() -> new NotFoundException(ErrorCodesEnum.SYSTEM_ERROR));

        String redisKey = String.format(TRX_QUEUE_REDIS_PATTERN_KEY, eventId);
        if (queue.getStatus() == QueueStatus.PROCESS) {
            Boolean exist = redisService.hexists(redisKey, queue.getQueueNumber().toString());
            if (!exist){
                queue.setStatus(QueueStatus.EXPIRED);
                queue.setUpdatedAt(DateTimeUtil.getCurrentDateTime());
                queue = eventQueueRepository.save(queue);
            }
        }

        if (queue.getStatus() != QueueStatus.WAITING) {
            return queue;
        }

        Long totalTransactionUser = redisService.hlen(redisKey);

        if (totalTransactionUser >= MAX_QUEUE_TRANSACTION_NUMBER) {
            return queue;
        }

        queue.setStatus(QueueStatus.PROCESS);
        queue.setBuyExpiredAt(DateTimeUtil.getCurrentDateTime().plusMinutes(EXPIRED_TIME_QUEUE_TRANSACTION));
        queue.setUpdatedAt(DateTimeUtil.getCurrentDateTime());
        eventQueueRepository.save(queue);

        HashMap<String, String> data = new HashMap<>();
        data.put(queue.getQueueNumber().toString(), "1");
        redisService.hsetex(redisKey, EXPIRED_TIME_QUEUE_TRANSACTION, data);

        return queue;
    }

    public boolean ReleaseQueue(Integer eventId, Integer queueNumber) {
        EventQueue queue = eventQueueRepository.findByEventIdAndQueueNumber(eventId, queueNumber)
                .orElseThrow(() -> new NotFoundException(ErrorCodesEnum.SYSTEM_ERROR));

        queue.setStatus(QueueStatus.DONE);
        eventQueueRepository.save(queue);

        redisService.hdel(String.format(TRX_QUEUE_REDIS_PATTERN_KEY, eventId), queueNumber.toString());
        return true;
    }
}

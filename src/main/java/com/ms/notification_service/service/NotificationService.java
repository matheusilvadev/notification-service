package com.ms.notification_service.service;


import com.ms.notification_service.dto.NotificationRequestDTO;
import com.ms.notification_service.enums.NotificationStatus;
import com.ms.notification_service.model.NotificationModel;
import com.ms.notification_service.repository.NotificationRepository;
import com.ms.notification_service.strategies.NotificationStrategyFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository repository;
    private final RabbitTemplate rabbitTemplate;
    private final NotificationStrategyFactory strategy;

    @Value("${notification.rabbitmq.exchange}")
    private String exchange;

    @Value("${notification.rabbitmq.routing-key}")
    private String routingKey;

    // Saves as pending and publishes to the queue for a rapid response (RNF < 100ms).
    @Transactional
    public NotificationModel enqueueNotification(NotificationRequestDTO dto) {
        NotificationModel model = new NotificationModel();
        model.setRecipient(dto.recipient());
        model.setSubject(dto.subject());
        model.setContent(dto.content());
        model.setChannel(dto.channel());
        model.setOriginService(dto.originService());
        model.setStatus(NotificationStatus.PENDING);
        model.setCreatedAt(LocalDateTime.now());

        model = repository.save(model);

        NotificationRequestDTO messagePayload = new NotificationRequestDTO(
                model.getId(),
                dto.recipient(),
                dto.subject(),
                dto.content(),
                dto.channel(),
                dto.originService()
        );

        // Publishes to the RabbitMQ queue.
        rabbitTemplate.convertAndSend(exchange, routingKey, messagePayload);

        return model;
    }

    // Background notification processing (Worker)
    public void processNotification(NotificationRequestDTO dto){
        log.info("Processing dispatch of [{}] via [{}] to: {}",
                dto.subject(), dto.channel(), dto.recipient());

        NotificationModel notification = repository.findById(dto.id())
                .orElseThrow(() -> new IllegalArgumentException("Notification not found with ID: " + dto.id()));


        try {
            strategy.getStrategy(dto.channel()).send(dto);
            notification.setStatus(NotificationStatus.SENT);

            repository.save(notification);

            log.info("Notification ID {} successfully delivered!", dto.id());
        } catch (Exception e){
            log.error("Error sending notification. Redirecting if retries are exhausted!");

            notification.setStatus(NotificationStatus.ERROR);
            repository.save(notification);

            throw e;
        }
    }

    private void sendExternalNotification(NotificationRequestDTO dto){
        if (dto.recipient().contains("error")){
            throw new RuntimeException("External API connection failure simulation!");
        }
    }
}

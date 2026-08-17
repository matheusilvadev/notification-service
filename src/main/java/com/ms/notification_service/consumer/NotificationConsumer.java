package com.ms.notification_service.consumer;


import com.ms.notification_service.dto.NotificationRequestDTO;
import com.ms.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationConsumer {
    private final NotificationService service;

    @RabbitListener(queues = "${notification.rabbitmq.queue}")
    public void consumeNotificationMessage(NotificationRequestDTO dto) {
        log.info("Message consumed from the RabbitMQ queue: ID {}", dto.id());
        service.processNotification(dto);
    }
}

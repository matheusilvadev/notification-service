package com.ms.notification_service.consumer;


import com.ms.notification_service.dto.NotificationRequestDTO;
import com.ms.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationConsumer {
    private final NotificationService service;

    @RabbitListener(queues = "${notification.rabbitmq.queue}")
    public void consumeNotificationMessage(NotificationRequestDTO dto) {
        service.processNotification(dto);
    }
}

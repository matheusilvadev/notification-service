package com.ms.notification_service.strategies.impl;


import com.ms.notification_service.dto.NotificationRequestDTO;
import com.ms.notification_service.enums.NotificationChannel;
import com.ms.notification_service.strategies.NotificationStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailNotificationStrategy implements NotificationStrategy {

    @Override
    public void send(NotificationRequestDTO dto) {
        log.info("📧 [SMTP Provider] Sending e-mail to: '{}' | Title: '{}'",
                dto.recipient(), dto.subject());

        if (dto.recipient() != null && dto.recipient().contains("error")) {
            throw new RuntimeException("Failed to communicate with the SMTP/SendGrid server!");
        }

        log.info("✅Email successfully delivered to {}", dto.recipient());
    }

    @Override
    public NotificationChannel getChannel() {
        return NotificationChannel.EMAIL;
    }
}

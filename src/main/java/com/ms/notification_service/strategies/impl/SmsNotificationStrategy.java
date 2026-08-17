package com.ms.notification_service.strategies.impl;

import com.ms.notification_service.dto.NotificationRequestDTO;
import com.ms.notification_service.enums.NotificationChannel;
import com.ms.notification_service.strategies.NotificationStrategy;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SmsNotificationStrategy implements NotificationStrategy {
    @Override
    public void send(NotificationRequestDTO dto) {
        log.info("[Twilio Provider] Sending SMS to number: '{}'", dto.recipient());
        log.info("💬 SMS Text: \"{}\"", dto.content());
        log.info("✅ SMS sent successfully!");
    }

    @Override
    public NotificationChannel getChannel() {
        return NotificationChannel.SMS;
    }
}

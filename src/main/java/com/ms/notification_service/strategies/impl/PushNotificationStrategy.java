package com.ms.notification_service.strategies.impl;

import com.ms.notification_service.dto.NotificationRequestDTO;
import com.ms.notification_service.enums.NotificationChannel;
import com.ms.notification_service.strategies.NotificationStrategy;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PushNotificationStrategy implements NotificationStrategy {
    @Override
    public void send(NotificationRequestDTO dto) {
        log.info("🔔 [FCM/Firebase] Sending push notification to device token: '{}'", dto.recipient());
        log.info("📱 Title: '{}' | Content: '{}'", dto.subject(), dto.content());
        log.info("✅ Push notification sent successfully!");
    }

    @Override
    public NotificationChannel getChannel() {
        return NotificationChannel.PUSH;
    }
}

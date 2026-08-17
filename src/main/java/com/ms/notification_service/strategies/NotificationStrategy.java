package com.ms.notification_service.strategies;

import com.ms.notification_service.dto.NotificationRequestDTO;
import com.ms.notification_service.enums.NotificationChannel;

public interface NotificationStrategy {
    void send(NotificationRequestDTO dto);
    NotificationChannel getChannel();
}

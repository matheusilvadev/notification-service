package com.ms.notification_service.dto;


import com.ms.notification_service.enums.NotificationChannel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record NotificationRequestDTO(
        UUID id,

        @NotBlank(message = "Recipient is mandatory")
        String recipient,

        @NotBlank(message = "Subject/title is mandatory")
        String subject,

        @NotBlank(message = "Text content is mandatory")
        String content,

        @NotNull(message = "Chanel is mandatory")
        NotificationChannel channel,

        String originService){

}


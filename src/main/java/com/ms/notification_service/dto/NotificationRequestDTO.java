package com.ms.notification_service.dto;


import com.ms.notification_service.enums.NotificationChannel;
import jakarta.validation.constraints.NotBlank;

public record NotificationRequestDTO(
        @NotBlank(message = "Recipient is mandatory")
        String recipient,

        @NotBlank(message = "Subject/title is mandatory")
        String subject,

        @NotBlank(message = "Text content is mandatory")
        String content,

        @NotBlank(message = "Chanel is mandatory")
        NotificationChannel channel,

        String originService){

}


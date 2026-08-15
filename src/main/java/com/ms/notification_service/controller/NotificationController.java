package com.ms.notification_service.controller;


import com.ms.notification_service.dto.NotificationRequestDTO;
import com.ms.notification_service.model.NotificationModel;
import com.ms.notification_service.repository.NotificationRepository;
import com.ms.notification_service.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService service;
    private final NotificationRepository repository;

    @PostMapping
    public ResponseEntity<NotificationModel> requestNotification(@RequestBody @Valid NotificationRequestDTO dto) {
        NotificationModel notification = service.enqueueNotification(dto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(notification);
    }

    @GetMapping
    public ResponseEntity<List<NotificationModel>> getAllNotifications() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationModel> getNotificationById(@PathVariable UUID id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

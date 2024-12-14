package com.zhiar.controller;

import com.zhiar.entity.Notification;
import com.zhiar.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/Notify")
    public ResponseEntity<List<Notification>> getUserNotifications() {
        List<Notification> notifications = notificationService.getNotificationsForLoggedInUser();
        return ResponseEntity.ok(notifications);
    }
}

package com.zhiar.service;

import com.zhiar.dao.NotificationDao;
import com.zhiar.dao.UserDao;
import com.zhiar.entity.Notification;
import com.zhiar.entity.User;
import com.zhiar.filter.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    private final NotificationDao notificationDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    public NotificationService(NotificationDao notificationDao) {
        this.notificationDao = notificationDao;
    }

    public void sendNotification(Integer userId, String message) {
        User user = userDao.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(message);
        notification.setIsRead(false);

        notificationDao.save(notification);
    }

    public List<Notification> getNotifications(Integer userId) {
        return notificationDao.findByUserIdAndIsRead(userId, false);
    }

    public void markAsRead(Integer notificationId) {
        Notification notification = notificationDao.findById(notificationId).orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setIsRead(true);
        notificationDao.save(notification);
    }

    public Integer getLoggedInUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getId();
    }
    public List<Notification> getNotificationsForLoggedInUser() {
        Integer loggedInUserId = getLoggedInUserId();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            return notificationDao.findAll();
        } else {
            return notificationDao.findByUserId(loggedInUserId);
        }
    }
}
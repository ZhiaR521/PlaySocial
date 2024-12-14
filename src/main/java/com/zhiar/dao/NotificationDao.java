package com.zhiar.dao;

import com.zhiar.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationDao extends JpaRepository<Notification, Integer> {
    List<Notification> findByUserIdAndIsRead(Integer userId, Boolean isRead);
    List<Notification> findByUserId(Integer userId);

}

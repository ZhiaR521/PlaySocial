package com.zhiar.dto;

import com.zhiar.entity.Notification;

public class NotificationDTO {
    private Integer id;
    private String message;
    private Boolean isRead;

    public NotificationDTO(Notification notification) {
        this.id = notification.getId();
        this.message = notification.getMessage();
        this.isRead = notification.getIsRead();
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Boolean getIsRead() { return isRead; }
    public void setIsRead(Boolean isRead) { this.isRead = isRead; }
}

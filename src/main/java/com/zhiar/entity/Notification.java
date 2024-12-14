package com.zhiar.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String message;
    private Boolean isRead;

    @Column(name = "receiver")
    private Integer receiver;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Notification() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Boolean getIsRead() { return isRead; }
    public void setIsRead(Boolean isRead) { this.isRead = isRead; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Integer getReceiver() { return receiver ;}
    public void setReceiver(Integer receiver) {this.receiver = receiver;}

    @Override
    public String toString() {
        return "Notification{id=" + id + ", user=" + user.getId() + ", message='" + message + "', isRead=" + isRead + "}";
    }
}

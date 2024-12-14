package com.zhiar.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
@Table(name = "friend_requests")
public class FriendRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer senderId;
    private Integer receiverId;
    private String status;

    public FriendRequest() {
    }

    public FriendRequest(Integer senderId, Integer receiverId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.status = "PENDING";
    }
}
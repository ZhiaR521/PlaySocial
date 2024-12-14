package com.zhiar.dao;

import com.zhiar.entity.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRequestDao extends JpaRepository<FriendRequest, Integer> {
    List<FriendRequest> findByReceiverIdAndStatus(Integer receiverId, String status);
    List<FriendRequest> findByReceiverIdAndSenderId(Integer receiverId, Integer senderId);
}

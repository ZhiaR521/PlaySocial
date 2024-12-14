package com.zhiar.service;

import com.zhiar.dao.UserBlockDao;
import com.zhiar.dao.UserDao;
import com.zhiar.entity.User;
import com.zhiar.entity.UserBlock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserBlockService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserBlockDao userBlockDao;

    public String blockUser(Integer blockerId, Integer blockedId) {
        User blocker = userDao.findById(blockerId)
                .orElseThrow(() -> new IllegalArgumentException("Blocker not found"));
        User blocked = userDao.findById(blockedId)
                .orElseThrow(() -> new IllegalArgumentException("Blocked user not found"));

        Optional<UserBlock> existingBlock = userBlockDao.findByBlockerAndBlocked(blocker, blocked);

        if (existingBlock.isPresent()) {
            return "User already blocked";
        }

        UserBlock userBlock = new UserBlock();
        userBlock.setBlocker(blocker);
        userBlock.setBlocked(blocked);
        userBlock.setBlockDate(LocalDateTime.now());

        userBlockDao.save(userBlock);
        return "User blocked successfully";
    }

    public List<UserBlock> getBlockedUsers(Integer userId) {
        User user = userDao.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return userBlockDao.findAllByBlocker(user);
    }

    public List<UserBlock> getBlockingUsers(Integer userId) {
        User user = userDao.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return userBlockDao.findAllByBlocked(user);
    }

    public void unblockUser(Integer blockerId, Integer blockedId) {
        Optional<UserBlock> blockEntry = userBlockDao.findByBlockerIdAndBlockedId(blockerId, blockedId);

        if (blockEntry.isPresent()) {
            userBlockDao.deleteByBlockerIdAndBlockedId(blockerId, blockedId);
        } else {
            throw new IllegalArgumentException("User is not blocked.");
        }
    }
}

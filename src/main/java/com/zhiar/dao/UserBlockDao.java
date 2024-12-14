package com.zhiar.dao;

import com.zhiar.entity.User;
import com.zhiar.entity.UserBlock;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserBlockDao  extends JpaRepository<UserBlock, Integer> {

    Optional<UserBlock> findByBlockerAndBlocked(User blocker, User blocked);
    List<UserBlock> findAllByBlocker(User blocker);
    List<UserBlock> findAllByBlocked(User blocked);
    @Modifying
    @Transactional
    void deleteByBlockerIdAndBlockedId(Integer blockerId, Integer blockedId);
    Optional<UserBlock> findByBlockerIdAndBlockedId(Integer blockerId, Integer blockedId);
}
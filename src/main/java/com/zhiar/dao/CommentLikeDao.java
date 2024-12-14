package com.zhiar.dao;

import com.zhiar.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeDao extends JpaRepository<CommentLike, Integer> {
    Optional<CommentLike> findByCommentIdAndUserId(Integer commentId, Integer userId);
    void deleteByCommentIdAndUserId(Integer commentId, Integer userId);
}

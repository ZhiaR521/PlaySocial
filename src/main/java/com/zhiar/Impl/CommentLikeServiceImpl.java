package com.zhiar.Impl;

import com.zhiar.dao.CommentLikeDao;
import com.zhiar.entity.CommentLike;
import com.zhiar.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentLikeServiceImpl implements CommentLikeService {

    private final CommentLikeDao commentLikeDao;

    @Override
    public void likeComment(Integer commentId, Integer userId) {
        Optional<CommentLike> existingLike = commentLikeDao.findByCommentIdAndUserId(commentId, userId);
        if (existingLike.isPresent()) {
            throw new RuntimeException("User has already liked this comment.");
        }

        CommentLike commentLike = new CommentLike();
        commentLike.setCommentId(commentId);
        commentLike.setUserId(userId);
        commentLikeDao.save(commentLike);
    }

    @Override
    public void unlikeComment(Integer commentId, Integer userId) {
        Optional<CommentLike> existingLike = commentLikeDao.findByCommentIdAndUserId(commentId, userId);
        if (!existingLike.isPresent()) {
            throw new RuntimeException("You have not liked this comment yet.");
        }

        commentLikeDao.delete(existingLike.get());
    }

    @Override
    public boolean isCommentLiked(Integer commentId, Integer userId) {
        return commentLikeDao.findByCommentIdAndUserId(commentId, userId).isPresent();
    }
}
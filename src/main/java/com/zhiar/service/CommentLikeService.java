package com.zhiar.service;

public interface CommentLikeService {
    void likeComment(Integer commentId, Integer userId);
    void unlikeComment(Integer commentId, Integer userId);
    boolean isCommentLiked(Integer commentId, Integer userId); // Add this line
}
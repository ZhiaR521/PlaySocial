package com.zhiar.service;

import com.zhiar.dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto addComment(CommentDto commentDto);
    void deleteComment(Integer commentId);
    List<CommentDto> getCommentsForPost(Integer postId);
}

package com.zhiar.Impl;


import com.zhiar.dao.CommentDao;
import com.zhiar.dto.CommentDto;
import com.zhiar.entity.Comment;
import com.zhiar.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentDao commentDao;

    @Override
    public CommentDto addComment(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        comment.setPostId(commentDto.getPostId());
        comment.setUserId(commentDto.getUserId());
        comment.setCreatedAt(LocalDateTime.now());
        commentDao.save(comment);
        return commentDto;
    }

    @Override
    public void deleteComment(Integer commentId) {
        commentDao.deleteById(commentId);
    }

    @Override
    public List<CommentDto> getCommentsForPost(Integer postId) {
        List<Comment> comments = commentDao.findByPostId(postId);
        return comments.stream().map(comment -> {
            CommentDto dto = new CommentDto();
            dto.setId(comment.getId());
            dto.setContent(comment.getContent());
            dto.setPostId(comment.getPostId());
            dto.setUserId(comment.getUserId());
            dto.setCreateAt(comment.getCreatedAt());
            return dto;
        }).toList();
    }
}

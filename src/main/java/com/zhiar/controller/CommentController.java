package com.zhiar.controller;


import com.zhiar.dto.CommentDto;
import com.zhiar.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/add")
    public CommentDto addComment(@RequestBody CommentDto commentDto) {
        return commentService.addComment(commentDto);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Integer commentId) {
        commentService.deleteComment(commentId);
    }

    @GetMapping("/post/{postId}")
    public List<CommentDto> getCommentsForPost(@PathVariable Integer postId) {
        return commentService.getCommentsForPost(postId);
    }
}
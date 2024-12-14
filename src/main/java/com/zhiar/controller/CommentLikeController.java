package com.zhiar.controller;

import com.zhiar.dao.CommentLikeDao;
import com.zhiar.filter.CustomUserDetails;
import com.zhiar.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentLikeController {

    private final CommentLikeService commentLikeService;
    private final CommentLikeDao commentLikeDao;

    @PostMapping("/{commentId}/like")
    public ResponseEntity<String> likeComment(@PathVariable Integer commentId, Authentication authentication) {
        Integer userId = getUserIdFromAuthentication(authentication);

        try {
            commentLikeService.likeComment(commentId, userId);
            return ResponseEntity.ok("Comment liked successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{commentId}/like")
    public ResponseEntity<String> unlikeComment(@PathVariable Integer commentId, Authentication authentication) {
        Integer userId = getUserIdFromAuthentication(authentication);
        commentLikeService.unlikeComment(commentId, userId);
        return ResponseEntity.ok("Comment unliked successfully.");
    }

    private Integer getUserIdFromAuthentication(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getId();
    }
}
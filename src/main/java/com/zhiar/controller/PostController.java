package com.zhiar.controller;

import com.zhiar.dto.PostDto;
import com.zhiar.dto.SavedPostDto;
import com.zhiar.filter.JwtFilter;
import com.zhiar.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final JwtFilter jwtFilter;

    @PostMapping("/create")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
        PostDto createdPost = postService.createPost(postDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Integer postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDto>> getUserPosts(@PathVariable Integer userId) {
        List<PostDto> userPosts = postService.getUserPosts(userId);
        return ResponseEntity.ok(userPosts);
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<String> likePost(@PathVariable Integer postId, @RequestParam Integer userId) {
        try {
            postService.likePost(postId, userId);
            return ResponseEntity.ok("Post liked successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping("/{postId}/unlike")
    public ResponseEntity<String> unlikePost(@PathVariable Integer postId, @RequestParam Integer userId) {
        postService.unlikePost(postId, userId);
        return ResponseEntity.ok("Post unliked successfully.");
    }


    @PostMapping("/save")
    public ResponseEntity<String> savePost(@RequestParam Integer postId) {
        String currentUserEmail = jwtFilter.getCurrentUser();
        postService.savePost(currentUserEmail, postId);
        return ResponseEntity.ok("Post saved successfully.");
    }

    @DeleteMapping("/remove/{savedPostId}")
    public ResponseEntity<String> removeSavedPost(@PathVariable Integer savedPostId) {
        String currentUserEmail = jwtFilter.getCurrentUser();
        postService.removeSavedPost(currentUserEmail, savedPostId);
        return ResponseEntity.ok("Saved post removed successfully.");
    }

    @GetMapping("/savedPosts")
    public ResponseEntity<List<SavedPostDto>> getSavedPosts() {
        String currentUserEmail = jwtFilter.getCurrentUser();
        List<SavedPostDto> savedPosts = postService.getSavedPosts(currentUserEmail);
        return ResponseEntity.ok(savedPosts);
    }
}
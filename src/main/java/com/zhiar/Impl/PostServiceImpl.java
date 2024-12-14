package com.zhiar.Impl;


import com.zhiar.config.ResourceNotFoundException;
import com.zhiar.dao.PostDao;
import com.zhiar.dao.PostLikeDao;
import com.zhiar.dao.SavedPostDao;
import com.zhiar.dao.UserDao;
import com.zhiar.dto.PostDto;
import com.zhiar.dto.SavedPostDto;
import com.zhiar.entity.*;
import com.zhiar.service.PostService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostDao postDao;
    private final PostLikeDao postLikeDao;
    private final UserDao userDao;
    private final SavedPostDao savedPostDao;

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = new Post();
        post.setContent(postDto.getContent());
        post.setUser(postDto.getUserId());
        post.setCreatedAt(LocalDateTime.now());
        postDao.save(post);
        return postDto;
    }

    @Override
    public void deletePost(Integer postId) {
        postDao.deleteById(postId);

        List<SavedPost> savedPosts = savedPostDao.findByPostId(postId);
        savedPostDao.deleteAll(savedPosts);
    }

    @Override
    public List<PostDto> getUserPosts(Integer userId) {
        List<Post> posts = postDao.findByUserId(userId);
        return posts.stream().map(post -> {
            PostDto dto = new PostDto();
            dto.setId(post.getId());
            dto.setContent(post.getContent());
            dto.setUserId(post.getUser().getId());
            dto.setCreateAt(post.getCreatedAt());
            dto.setLikes(post.getLikes());
            return dto;
        }).toList();
    }

    @Override
    public void likePost(Integer postId, Integer userId) {
        Optional<PostLike> existingLike = postLikeDao.findByPostIdAndUserId(postId, userId);
        if (existingLike.isPresent()) {
            throw new IllegalArgumentException("You have already liked this post.");
        }

        PostLike postLike = new PostLike();
        postLike.setPostId(postId);
        postLike.setUserId(userId);
        postLikeDao.save(postLike);
    }

    @Override
    @Transactional
    public void unlikePost(Integer postId, Integer userId) {
        Optional<Post> post = postDao.findById(postId);
        if (post.isPresent()) {
            Optional<PostLike> postLike = postLikeDao.findByPostIdAndUserId(postId, userId);

            if (postLike.isPresent()) {
                postLikeDao.delete(postLike.get());
                System.out.println("Post unliked successfully by user " + userId);
            } else {
                System.out.println("User " + userId + " has not liked post " + postId);
            }
        } else {
            throw new ResourceNotFoundException("Post not found with ID: " + postId);
        }
    }


    @Override
    public List<Post> searchPostsByUsernameOrContent(String keyword) {
        List<PostInfo> postInfos = new ArrayList<>();

        List<Post> postsByContent = postDao.findByContentContainingIgnoreCase(keyword);
        for (Post post : postsByContent) {
            String name = userDao.findById(post.getUser().getId())
                    .map(User::getName)
                    .orElse("Unknown User");
            postInfos.add(new PostInfo(name, post));
        }

        List<User> users = userDao.findByUsernameContainingIgnoreCase(keyword);
        for (User user : users) {
            List<Post> userPosts = postDao.findByUserId(user.getId());
            for (Post post : userPosts) {
                postInfos.add(new PostInfo(user.getName(), post));
            }
        }

        return postDao.findByUsernameOrContent(keyword);
    }

    @Override
    public List<Post> searchPostsByContent(String keyword) {
        return postDao.findByContentContainingIgnoreCase(keyword);
    }

    @Override
    public void savePost(String userEmail, Integer postId) {
        User user = userDao.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found"));
        Post post = postDao.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));

        if (savedPostDao.findByUserIdAndPostId(user.getId(), postId).isPresent()) {
            throw new RuntimeException("Post is already saved.");
        }

        SavedPost savedPost = new SavedPost();
        savedPost.setUser(user);
        savedPost.setPost(post);
        savedPostDao.save(savedPost);
    }

    @Override
    public void removeSavedPost(String userEmail, Integer savedPostId) {
        User user = userDao.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found"));
        SavedPost savedPost = savedPostDao.findById(savedPostId)
                .orElseThrow(() -> new RuntimeException("Saved post not found"));

        if (!savedPost.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You can only remove your own saved posts.");
        }

        savedPostDao.delete(savedPost);
    }

    @Override
    public List<SavedPostDto> getSavedPosts(String userEmail) {
        User user = userDao.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<SavedPost> savedPosts = savedPostDao.findByUserId(user.getId());

        return savedPosts.stream()
                .map(savedPost -> {
                    Post post = savedPost.getPost();
                    return new SavedPostDto(
                            savedPost.getId(),
                            post.getId(),
                            post.getTitle(),
                            post.getContent(),
                            post.getLikes(),
                            post.getUser().getName()
                    );
                })
                .collect(Collectors.toList());
    }
}

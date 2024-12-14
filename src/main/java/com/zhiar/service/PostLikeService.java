package com.zhiar.service;

import com.zhiar.dao.PostLikeDao;
import com.zhiar.entity.PostLike;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostLikeService {

    @Autowired
    private PostLikeDao postLikeDao;

    public void likePost(Integer postId, Integer userId) {
        Optional<PostLike> existingLike = postLikeDao.findByPostIdAndUserId(postId, userId);
        if (existingLike.isPresent()) {
            throw new IllegalArgumentException("User has already liked this post.");
        }
        PostLike postLike = new PostLike();
        postLike.setPostId(postId);
        postLike.setUserId(userId);
        postLikeDao.save(postLike);
    }

    @Transactional
    public void unlikePost(Integer postId, Integer userId) {
        postLikeDao.deleteByPostIdAndUserId(postId, userId);
    }
}

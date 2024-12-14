package com.zhiar.service;

import com.zhiar.dto.PostDto;
import com.zhiar.dto.SavedPostDto;
import com.zhiar.entity.Post;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);
    void deletePost(Integer postId);
    List<PostDto> getUserPosts(Integer userId);
    void likePost(Integer postId, Integer userId);
    void unlikePost(Integer postId, Integer userId);
    List<Post> searchPostsByContent(String keyword);
    List<Post> searchPostsByUsernameOrContent(String keyword);
    void savePost(String userEmail, Integer postId);
    void removeSavedPost(String userEmail, Integer savedPostId);
    List<SavedPostDto> getSavedPosts(String userEmail);

}

package com.zhiar.dto;

import java.util.Set;

public class SavedPostDto {
    private Integer savedPostId;
    private Integer postId;
    private String title;
    private String content;
    private Set<Integer> likes;
    private String postedBy;

    public SavedPostDto(Integer savedPostId, Integer postId, String title, String content, Set<Integer> likes, String postedBy) {
        this.savedPostId = savedPostId;
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.likes = likes;
        this.postedBy = postedBy;
    }

    public Integer getSavedPostId() {
        return savedPostId;
    }

    public void setSavedPostId(Integer savedPostId) {
        this.savedPostId = savedPostId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<Integer> getLikes() {
        return likes;
    }

    public void setLikes(Set<Integer> likes) {
        this.likes = likes;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }
}

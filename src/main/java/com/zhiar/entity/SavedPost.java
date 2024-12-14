package com.zhiar.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class SavedPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    private LocalDateTime SavedAt = LocalDateTime.now();

    public SavedPost(Integer id, User user, Post post, LocalDateTime savedAt) {
        this.id = id;
        this.user = user;
        this.post = post;
        SavedAt = savedAt;
    }

    public SavedPost() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public LocalDateTime getSavedAt() {
        return SavedAt;
    }

    public void setSavedAt(LocalDateTime savedAt) {
        SavedAt = savedAt;
    }
}

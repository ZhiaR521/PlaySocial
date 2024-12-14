package com.zhiar.entity;

public class PostInfo {
    private String name;
    private Post post;

    public PostInfo(String name, Post post) {
        this.name = name;
        this.post = post;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}

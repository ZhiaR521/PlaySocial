package com.zhiar.entity;

import java.util.List;


public class SearchResults {
    private List<User> users;
    private List<PostInfo> posts;

    public SearchResults(List<User> users, List<PostInfo> posts) {
        this.users = users;
        this.posts = posts;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<PostInfo> getPosts() {
        return posts;
    }

    public void setPosts(List<PostInfo> posts) {
        this.posts = posts;
    }
}
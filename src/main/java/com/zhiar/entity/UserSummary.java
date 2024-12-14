package com.zhiar.entity;


public class UserSummary {
    private String name;
    private Integer userId;
    private int friendCount;



    public UserSummary(String name, Integer userId, int friendCount) {
        this.name = name;
        this.userId = userId;
        this.friendCount = friendCount;
    }

    public String getUsername() {
        return name;
    }

    public void setUsername(String name) {
        this.name = name;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public int getFriendCount() {
        return friendCount;
    }

    public void setFriendCount(int friendCount) {
        this.friendCount = friendCount;
    }
}
package com.zhiar.entity;

import lombok.Data;

@Data
public class PostSummary {
    private String content;
    private Integer userId;
    private String name;

    public PostSummary(String content, Integer userId, String name) {
        this.content = content;
        this.userId = userId;
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

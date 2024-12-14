package com.zhiar.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class PostDto {

    private Integer id;

    private String content;

    private Integer userId;

    private LocalDateTime createAt;

    private Set<Integer> likes;

}

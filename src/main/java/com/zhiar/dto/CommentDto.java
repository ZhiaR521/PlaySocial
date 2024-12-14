package com.zhiar.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {

    private Integer id;

    private String content;

    private Integer postId;

    private Integer userId;

    private LocalDateTime createAt;

}

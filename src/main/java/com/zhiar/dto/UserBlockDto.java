package com.zhiar.dto;

import lombok.Data;

@Data
public class UserBlockDto {
    private Integer blockerId;
    private Integer blockedId;
}
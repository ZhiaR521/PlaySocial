package com.zhiar.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Integer id;
    private String email;
    private String name;
    private String password;
    private List<Integer> friends;

}
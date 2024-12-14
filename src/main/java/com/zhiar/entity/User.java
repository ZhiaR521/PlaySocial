package com.zhiar.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String email;

    private String password;

    private String name;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    public User() {
    }

    @ElementCollection
    private Set<Integer> friends = new HashSet<>();

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications = new ArrayList<>();


    public User(Integer id, String name, Set<User> friends , Set<User> friend) {
        this.id = id;
        this.name = name;
    }
}

package com.zhiar.dao;

import com.zhiar.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostDao extends JpaRepository<Post , Integer> {

    List<Post> findByUserId(Integer userId);

    List<Post> findByUserId(Long userId);

    @Query("SELECT p FROM Post p WHERE LOWER(p.content) LIKE LOWER(CONCAT('%', :name, '%')) OR p.user.name LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Post> searchByUsernameOrContent(@Param("name") String name);

    @Query("SELECT p FROM Post p WHERE LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Post> findByContentContainingIgnoreCase(@Param("keyword") String keyword);

    @Query("SELECT p FROM Post p WHERE LOWER(p.content) LIKE LOWER(CONCAT('%', :username, '%')) OR p.user.username LIKE LOWER(CONCAT('%', :username, '%'))")
    List<Post> findByUsernameOrContent(@Param("username") String username);
}


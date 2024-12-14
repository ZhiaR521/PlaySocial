package com.zhiar.dao;

import com.zhiar.entity.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface UserDao extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String name);
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<User> findByUsernameContainingIgnoreCase(@Param("name") String name);

    @NotNull
    Optional<User> findById(@NotNull Integer id);

}


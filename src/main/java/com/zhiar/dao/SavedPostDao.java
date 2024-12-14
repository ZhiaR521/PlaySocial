package com.zhiar.dao;

import com.zhiar.entity.SavedPost;
import com.zhiar.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavedPostDao extends JpaRepository<SavedPost,Integer> {

    List<SavedPost> findByUserId(Integer id);
    Optional<SavedPost> findByUserIdAndPostId(Integer userId , Integer postId);
    void deleteByUserAndPostId(User user , Integer postId);
    List<SavedPost> findByPostId(Integer postId);

}

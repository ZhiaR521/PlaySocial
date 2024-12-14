package com.zhiar.dao;

import com.zhiar.entity.PostLike;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PostLikeDao {
    @PersistenceContext
    @Qualifier
    private EntityManager entityManager;

    @Transactional
    public void save(PostLike postLike) {
        entityManager.persist(postLike);
    }

    public Optional<PostLike> findByPostIdAndUserId(Integer postId, Integer userId) {
        TypedQuery<PostLike> query = entityManager.createQuery(
                "SELECT pl FROM PostLike pl WHERE pl.postId = :postId AND pl.userId = :userId", PostLike.class);
        query.setParameter("postId", postId);
        query.setParameter("userId", userId);
        return query.getResultStream().findFirst();
    }

    @Transactional
    public void delete(PostLike postLike) {
        entityManager.remove(entityManager.contains(postLike) ? postLike : entityManager.merge(postLike));
    }

    @Transactional
    public void deleteByPostIdAndUserId(Integer postId, Integer userId) {
        Optional<PostLike> postLikeOptional = findByPostIdAndUserId(postId, userId);
        postLikeOptional.ifPresent(this::delete);
    }
}
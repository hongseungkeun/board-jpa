package com.board.application.like.repository;

import com.board.application.like.domain.Like;
import com.board.application.post.domain.Post;
import com.board.application.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long>{
    Optional<Like> findByUserAndPost(User user, Post post);
}

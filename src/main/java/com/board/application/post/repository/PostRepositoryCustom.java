package com.board.application.post.repository;

import com.board.application.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {
    Page<Post> findPostsBySearchWord(String searchWord, Pageable pageable);
}

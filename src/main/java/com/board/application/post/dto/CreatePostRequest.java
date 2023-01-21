package com.board.application.post.dto;

import com.board.application.post.domain.Post;
import com.board.application.user.domain.User;
import jakarta.validation.constraints.NotBlank;

public record CreatePostRequest(@NotBlank String title, @NotBlank String content, Long id) {
    public Post toPost(User user){
        return new Post(this.title, this.content, user);
    }
}

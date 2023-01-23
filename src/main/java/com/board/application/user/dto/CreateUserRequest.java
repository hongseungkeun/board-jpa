package com.board.application.user.dto;

import com.board.application.user.domain.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserRequest(@NotBlank String name, String hobby, @NotNull Integer age) {
    public User toUser(){
        return new User(this.name, this.hobby, this.age);
    }
}

package com.board.application.user.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginUserRequest(@NotBlank String email, @NotBlank String password) {
}

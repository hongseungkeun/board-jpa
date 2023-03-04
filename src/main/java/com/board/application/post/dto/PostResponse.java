package com.board.application.post.dto;

public record PostResponse(String title, String content, String created_at, String created_by) {
}

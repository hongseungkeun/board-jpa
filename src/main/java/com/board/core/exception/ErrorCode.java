package com.board.core.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시물이 존재하지 않습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저가 존재하지 않습니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message){
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}

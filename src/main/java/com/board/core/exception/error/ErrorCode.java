package com.board.core.exception.error;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시물이 존재하지 않습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저가 존재하지 않습니다."),
    EXIST_USER(HttpStatus.CONFLICT, "이미 존재하는 이메일 입니다."),
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "잘못된 비밀번호 입니다."),
    REQUIRED_LOGIN(HttpStatus.UNAUTHORIZED, "로그인 후 이용 가능합니다."),
    AUTH_FAILED(HttpStatus.UNAUTHORIZED, "권한이 없습니다.");

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
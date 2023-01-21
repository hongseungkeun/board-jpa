package com.board.core.exception;

public class PostNotFoundException extends CustomException{
    public PostNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}

package com.board.core.exception;

public class UserNotFoundException extends CustomException{
    public UserNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
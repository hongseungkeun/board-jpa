package com.board.core.exception;

import com.board.core.exception.error.ErrorCode;

public class UserNotFoundException extends CustomException{
    public UserNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
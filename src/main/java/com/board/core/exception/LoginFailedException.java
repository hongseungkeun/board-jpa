package com.board.core.exception;

import com.board.core.exception.error.ErrorCode;

public class LoginFailedException extends CustomException{
    public LoginFailedException(ErrorCode errorCode) {
        super(errorCode);
    }
}

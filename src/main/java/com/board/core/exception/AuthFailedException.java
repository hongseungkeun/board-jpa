package com.board.core.exception;

import com.board.core.exception.error.ErrorCode;

public class AuthFailedException extends CustomException{
    public AuthFailedException(ErrorCode errorCode) {
        super(errorCode);
    }
}

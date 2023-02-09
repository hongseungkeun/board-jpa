package com.board.core.exception;

public class AuthFailedException extends CustomException{
    public AuthFailedException(ErrorCode errorCode) {
        super(errorCode);
    }
}

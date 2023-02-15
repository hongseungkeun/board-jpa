package com.board.core.exception;

import com.board.core.exception.error.ErrorCode;

public class CustomException extends RuntimeException{
    private final ErrorCode errorCode;

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public CustomException(ErrorCode errorCode){
        this.errorCode = errorCode;
    }
}

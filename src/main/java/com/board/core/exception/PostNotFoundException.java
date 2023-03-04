package com.board.core.exception;

import com.board.core.exception.error.ErrorCode;

public class PostNotFoundException extends CustomException{
    public PostNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}

package com.board.core.exception;

import com.board.core.exception.error.ErrorCode;

public class AlreadyExistUserException extends CustomException{
    public AlreadyExistUserException(ErrorCode errorCode) {
        super(errorCode);
    }
}

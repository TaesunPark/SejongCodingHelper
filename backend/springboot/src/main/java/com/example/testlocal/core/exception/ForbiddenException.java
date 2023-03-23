package com.example.testlocal.core.exception;

public class ForbiddenException extends BusinessException {

    public ForbiddenException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public ForbiddenException(String message){
        super(message, ErrorCode.FORBIDDEN_EXCEPTION);
    }
}

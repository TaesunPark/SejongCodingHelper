package com.example.testlocal.core.exception;

public class UnauthorizedException extends BusinessException{
    public UnauthorizedException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public UnauthorizedException(String message){
        super(message, ErrorCode.UNAUTHORIZED_EXCEPTION);
    }
}

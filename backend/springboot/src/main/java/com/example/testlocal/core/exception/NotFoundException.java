package com.example.testlocal.core.exception;

public class NotFoundException extends BusinessException{

    public NotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public NotFoundException(String message){
        super(message, ErrorCode.NOT_FOUND_EXCEPTION);
    }
}

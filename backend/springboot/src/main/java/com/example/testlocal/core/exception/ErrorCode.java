package com.example.testlocal.core.exception;

import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public enum ErrorCode {
    ;

    private final HttpStatus httpStatus;
    private final String message;
    private final String code;

    ErrorCode(HttpStatus status, String code, String message) {
        this.httpStatus = status;
        this.message = message;
        this.code = code;
    }
}

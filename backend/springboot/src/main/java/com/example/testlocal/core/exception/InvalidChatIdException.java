package com.example.testlocal.core.exception;

public class InvalidChatIdException extends RuntimeException{
    public InvalidChatIdException(){
        super("존재하지 않는 Chat의 ID입니다.");
    }
}

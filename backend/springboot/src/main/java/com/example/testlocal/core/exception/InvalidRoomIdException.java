package com.example.testlocal.core.exception;

public class InvalidRoomIdException extends RuntimeException {
    public InvalidRoomIdException(){
        super("존재하지 않는 Room ID입니다.");
    }

}

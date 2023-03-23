package com.example.testlocal.module.user.application.dto.response;

import lombok.*;

@ToString
@Getter
public class EmailCodeResponse {
    private String authCode;

    public EmailCodeResponse(String authCode){
        this.authCode = authCode;
    }
}

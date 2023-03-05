package com.example.testlocal.module.user.application.dto.signup.response;

import lombok.*;

@ToString
@Getter
public class EmailCodeResponse {
    private String authCode;

    public EmailCodeResponse(String authCode){
        this.authCode = authCode;
    }
}

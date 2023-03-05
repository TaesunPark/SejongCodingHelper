package com.example.testlocal.module.user.application.dto.signup.response;

import lombok.*;

@ToString
@Getter
public class EmailCheckResponse {
    private String email;
    public EmailCheckResponse(String email){
        this.email = email;
    }
}

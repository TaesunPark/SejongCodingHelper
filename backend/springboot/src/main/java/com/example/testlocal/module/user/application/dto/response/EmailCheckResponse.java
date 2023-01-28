package com.example.testlocal.module.user.application.dto.response;

import lombok.*;

@ToString
@Getter
public class EmailCheckResponse {
    private String email;

    public EmailCheckResponse(String email){
        this.email = email;
    }
}

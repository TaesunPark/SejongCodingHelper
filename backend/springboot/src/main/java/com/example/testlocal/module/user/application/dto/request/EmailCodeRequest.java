package com.example.testlocal.module.user.application.dto.signup.request;

import lombok.Data;

@Data
public class EmailCodeRequest {
    private String authCode;
    private String email;
}

package com.example.testlocal.module.user.application.dto.request;

import lombok.Data;

@Data
public class EmailCodeRequest {
    private String authCode;
    private String email;
}

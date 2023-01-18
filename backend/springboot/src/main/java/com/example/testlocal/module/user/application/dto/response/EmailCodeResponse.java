package com.example.testlocal.module.user.application.dto.response;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class EmailCodeResponse {
    private String authCode;
    private Boolean isSuccessful;

    public static EmailCodeResponse of(String authCode, Boolean isSuccessful) {
        return EmailCodeResponse.builder()
                .isSuccessful(isSuccessful)
                .authCode(authCode)
                .build();
    }
}

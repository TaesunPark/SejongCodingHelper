package com.example.testlocal.module.user.application.dto.response;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class SendEmailResponse {

    private String email;

    public static SendEmailResponse of(String email) {
        return SendEmailResponse.builder()
                .email(email)
                .build();
    }
}

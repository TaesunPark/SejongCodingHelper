package com.example.testlocal.module.user.application.dto.response;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class EmailCheckResponse {
    private String email;
    private Boolean isDuplicated;

    public static EmailCheckResponse of(String email, Boolean isDuplicated) {
        return EmailCheckResponse.builder()
                .isDuplicated(isDuplicated)
                .email(email)
                .build();
    }
}

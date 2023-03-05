package com.example.testlocal.module.user.application.dto.signup.response;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class UserInfoResponse {

    private String studentNumber;
    private Boolean isSuccessful;
    private String message;

    public static UserInfoResponse of(String studentNumber, Boolean isSuccessful, String message) {
        return UserInfoResponse.builder()
                .message(message)
                .isSuccessful(isSuccessful)
                .studentNumber(studentNumber)
                .build();
    }
}

package com.example.testlocal.module.user.application.dto.response;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class UserInfoResponse {

    private String studentNumber;
    private Boolean isSuccessful;

    public static UserInfoResponse of(String studentNumber, Boolean isSuccessful) {
        return UserInfoResponse.builder()
                .isSuccessful(isSuccessful)
                .studentNumber(studentNumber)
                .build();
    }
}

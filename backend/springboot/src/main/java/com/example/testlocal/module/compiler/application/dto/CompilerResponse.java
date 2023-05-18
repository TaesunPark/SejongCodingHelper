package com.example.testlocal.module.compiler.application.dto;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class CompilerResponse {

    private Boolean isSuccessful;
    private String message;

    public static CompilerResponse of(Boolean isSuccessful, String message) {
        return CompilerResponse.builder()
                .message(message)
                .isSuccessful(isSuccessful)
                .build();
    }
}


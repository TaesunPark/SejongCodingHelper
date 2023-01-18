package com.example.testlocal.core.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.example.testlocal.core.dto.SuccessStatusCode.CREATED;
import static com.example.testlocal.core.dto.SuccessStatusCode.OK;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessCode {

    /**
     * 200 OK
     */
    OK_SUCCESS(OK, "성공입니다."),
    EMAIL_SUCCESS(OK, "이메일 보내기 성공입니다."),
    EMAIL_CODE_SUCCESS(OK, "이메일 검증 성공입니다."),
    LOGIN_SUCCESS(OK, "로그인 성공입니다."),
    FIND_MBR_SUCCESS(OK, "멤버 조회 성공입니다."),

    /**
     * 201 CREATED
     */
    CREATED_SUCCESS(CREATED, "생성 성공입니다."),

    /**
     * 202 ACCEPTED
     */

    /**
     * 204 NO_CONTENT
     */
    ;

    private final SuccessStatusCode statusCode;
    private final String message;

    public int getStatus() {
        return statusCode.getStatus();
    }
}

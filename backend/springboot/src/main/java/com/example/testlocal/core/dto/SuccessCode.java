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
    EMAIL_DUPLICATED_CODE_SUCCESS(OK, "이메일 중복 검사 성공입니다."),
    LOGIN_SUCCESS(OK, "로그인 성공입니다."),

    FIND_MBR_SUCCESS(OK, "멤버 조회 성공입니다."),
    SIGNUP_SUCCESS(OK, "회원가입 성공입니다.")
    ,
    CHAT_GPT_MESSAGE_STUDENT_ID_SUCCESS(OK, "학번으로 CHATGPT 채팅 불러오기 성공입니다.")
    ,
    REFRESH_TOKEN_SUCCESS(OK, "리프레시 토큰 발급 성공입니다."),
    TOKEN_SUCCESS(OK, "토큰 발급 성공입니다"),

    COMPILER_GCC_SUCCESS(OK, "C언어 실행 성공입니다."),
    COMPILER_PYTHON_SUCCESS(OK, "Python3 언어 실행 성공입니다.")


    ,

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

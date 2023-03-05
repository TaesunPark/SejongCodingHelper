package com.example.testlocal.module.chatgpt.application.dto.response;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ChatsByStudentIdResponse {

    private String message;
    private String createTime;
    private String recommends;

}

package com.example.testlocal.module.chatgpt.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.List;

@ToString
@Getter
@Builder
public class ChatGPTMessageResponse {
    Timestamp createTime;
    String message;
    String recommends;
    List<String> recoList;
}

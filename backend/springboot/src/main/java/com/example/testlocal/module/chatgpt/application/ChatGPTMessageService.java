package com.example.testlocal.module.chatgpt.application;

import com.example.testlocal.core.dto.SuccessResponse;
import com.example.testlocal.module.chatgpt.application.dto.response.ChatsByStudentIdResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ChatGPTMessageService {



    public ResponseEntity<SuccessResponse<ChatsByStudentIdResponse>> getChatsByStudentId(String studentId) {
        return null;
    }
}

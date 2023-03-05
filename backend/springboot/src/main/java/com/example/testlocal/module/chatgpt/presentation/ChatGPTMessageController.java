package com.example.testlocal.module.chatgpt.presentation;

import com.example.testlocal.config.Constants;
import com.example.testlocal.core.dto.SuccessResponse;
import com.example.testlocal.domain.entity.Chat;
import com.example.testlocal.module.chatgpt.application.ChatGPTMessageService;
import com.example.testlocal.module.chatgpt.application.dto.response.ChatsByStudentIdResponse;
import com.example.testlocal.module.user.application.dto.response.EmailCheckResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/chatgpt/students")
@CrossOrigin(origins = Constants.URL , allowCredentials = "true")
public class ChatGPTMessageController {

    private final ChatGPTMessageService chatGPTMessageService;

    public ChatGPTMessageController(ChatGPTMessageService chatGPTMessageService) {
        this.chatGPTMessageService = chatGPTMessageService;
    }

    @GetMapping("/{studentId}/chats")
    public ResponseEntity<SuccessResponse<ChatsByStudentIdResponse>> getChatsByStudentId(@PathVariable String studentId) {
        return chatGPTMessageService.getChatsByStudentId(studentId);
    }

//    @PostMapping("/{studentId}/chats")
//    public void sendChatMessage(@PathVariable String studentId, @RequestBody ChatMessageRequest request) {
//        chatService.sendChatMessage(studentId, request.getMessage());
//    }
}

package com.example.testlocal.module.chatgpt.presentation;

import com.example.testlocal.config.Constants;
import com.example.testlocal.core.dto.SuccessCode;
import com.example.testlocal.core.dto.SuccessResponse;
import com.example.testlocal.core.security.service.JwtTokenService;
import com.example.testlocal.module.chatgpt.application.ChatGPTMessageService;
import com.example.testlocal.module.chatgpt.application.dto.response.ChatsByStudentIdResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/chatgpt/students")
@CrossOrigin(origins = Constants.URL , allowCredentials = "true")
public class ChatGPTMessageController {

    private final ChatGPTMessageService chatGPTMessageService;
    private final JwtTokenService jwtTokenService;

    public ChatGPTMessageController(ChatGPTMessageService chatGPTMessageService, JwtTokenService jwtTokenService) {
        this.chatGPTMessageService = chatGPTMessageService;
        this.jwtTokenService = jwtTokenService;
    }

    @GetMapping("/chats")
    public ResponseEntity<SuccessResponse<List<ChatsByStudentIdResponse>>> getChatsByStudentId(@CookieValue(name = "refreshToken", defaultValue = "-1") String refreshToken) {
        String studentId = jwtTokenService.refreshTokenToStudentNumber(refreshToken);
        return SuccessResponse.success(SuccessCode.CHAT_GPT_MESSAGE_STUDENT_ID_SUCCESS, chatGPTMessageService.getChatsByStudentId(studentId));
    }

//    @PostMapping("/{studentId}/chats")
//    public void sendChatMessage(@PathVariable String studentId, @RequestBody ChatMessageRequest request) {
//        chatService.sendChatMessage(studentId, request.getMessage());
//    }
}

package com.example.testlocal.module.chatgpt.presentation;

import com.example.testlocal.core.security.jwt.service.JwtTokenService;
import com.example.testlocal.module.chatgpt.MyChatGPTService;
import com.example.testlocal.module.chatgpt.application.dto.request.ChatGPTMessageRequest;
import com.example.testlocal.module.chatgpt.application.dto.response.ChatGPTMessageResponse;
import com.example.testlocal.util.Constants;
import com.example.testlocal.core.dto.SuccessCode;
import com.example.testlocal.core.dto.SuccessResponse;
import com.example.testlocal.module.chatgpt.application.ChatGPTMessageService;
import com.example.testlocal.module.chatgpt.application.dto.response.ChatsByStudentIdResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = Constants.URL, allowCredentials = "true")
@RestController
@RequestMapping("/v1/chatgpt/students")
public class ChatGPTMessageController {

    private final ChatGPTMessageService chatGPTMessageService;
    private final JwtTokenService jwtTokenService;
    private final MyChatGPTService myChatGPTService;

    public ChatGPTMessageController(ChatGPTMessageService chatGPTMessageService, JwtTokenService jwtTokenService, MyChatGPTService myChatGPTService) {
        this.chatGPTMessageService = chatGPTMessageService;
        this.jwtTokenService = jwtTokenService;
        this.myChatGPTService = myChatGPTService;
    }

    @PostMapping("/chats")
    public ResponseEntity<SuccessResponse<List<ChatsByStudentIdResponse>>> getChatsByStudentId(@CookieValue(name = "refreshToken", defaultValue = "-1") String refreshToken) {
        String studentId = jwtTokenService.refreshTokenToStudentNumber(refreshToken);
        System.out.print(studentId);
        return SuccessResponse.success(SuccessCode.CHAT_GPT_MESSAGE_STUDENT_ID_SUCCESS, chatGPTMessageService.getChatsByStudentId(studentId));
    }

    @PostMapping("/chat")
    public ResponseEntity<SuccessResponse<ChatGPTMessageResponse>> sendToChatGPT(@RequestBody ChatGPTMessageRequest chatGPTMessageRequest) {
        return SuccessResponse.success(SuccessCode.CHAT_GPT_MESSAGE_STUDENT_ID_SUCCESS, myChatGPTService.chat(chatGPTMessageRequest.getMessage()));
    }



//    @PostMapping("/{studentId}/chats")
//    public void sendChatMessage(@PathVariable String studentId, @RequestBody ChatMessageRequest request) {
//        chatService.sendChatMessage(studentId, request.getMessage());
//    }
}

package com.example.testlocal.module.chatgpt.application;

import com.example.testlocal.core.dto.SuccessResponse;
import com.example.testlocal.module.chatgpt.application.dto.response.ChatsByStudentIdResponse;
import com.example.testlocal.module.chatgpt.domain.repository.ChatGPTMessageRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatGPTMessageService {
    private final ChatGPTMessageRepository chatGPTMessageRepository;

    public ChatGPTMessageService(ChatGPTMessageRepository chatGPTMessageRepository) {
        this.chatGPTMessageRepository = chatGPTMessageRepository;
    }

    public List<ChatsByStudentIdResponse> getChatsByStudentId(String studentId) {

        List<ChatsByStudentIdResponse> chatsByStudentIdResponses = new ArrayList<>();

        chatGPTMessageRepository.getUserChatGPTMessagesByUser_StudentNumber(studentId).get().forEach(
                userChatGPTMessage -> chatsByStudentIdResponses.add(new ChatsByStudentIdResponse(userChatGPTMessage.getQuestion(), userChatGPTMessage.getCreateAt().toString(), userChatGPTMessage.getQuestion()))
        );

        return chatsByStudentIdResponses;
    }

    public void saveChatGPTMessage(String question, String answer){
        // User,Chatgpt

        // chatGPTMessageRepository.save();
    }
}

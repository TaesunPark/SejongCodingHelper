package com.example.testlocal.module.chatgpt;

import io.github.flashvayne.chatgpt.dto.ChatRequest;
import io.github.flashvayne.chatgpt.service.ChatgptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatGPTExample {
    private ChatgptService chatgptService;

    @Autowired
    public ChatGPTExample(ChatgptService chatgptService){
        this.chatgptService = chatgptService;
    }

    public String chat(String prompt){
        // ChatGPT 에게 질문을 던집니다.
        String responseMessage = chatgptService.sendMessage(prompt);
        return responseMessage;
    }

}

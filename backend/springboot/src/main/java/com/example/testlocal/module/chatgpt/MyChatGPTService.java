package com.example.testlocal.module.chatgpt;

import com.example.testlocal.module.chatgpt.application.ChatGPTMessageService;
import com.example.testlocal.module.chatgpt.application.dto.response.ChatGPTMessageResponse;
import io.github.flashvayne.chatgpt.property.ChatgptProperties;
import io.github.flashvayne.chatgpt.service.ChatgptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class
MyChatGPTService {
    private ChatgptService chatgptService;
    private ChatgptProperties properties;
    private ChatGPTMessageService chatGPTMessageService;

    @Autowired
    public MyChatGPTService(ChatgptService chatgptService, ChatgptProperties properties){
        this.chatgptService = chatgptService;
        this.properties = properties;
        properties.setMaxTokens(1000);
    }

    public ChatGPTMessageResponse chat(String prompt){
        String responseMessage = chatgptService.sendMessage(prompt);
        List<String> list = new ArrayList<>();
        list.add("추천 1");
        list.add("추천 2");
        list.add("추천 3");
        list.add("추천 4");

        Timestamp time = new Timestamp(System.currentTimeMillis());
        return ChatGPTMessageResponse.builder().message(prompt).recommends(responseMessage).createTime(time).recoList(list).build();
    }

}

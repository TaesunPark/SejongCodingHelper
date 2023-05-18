package com.example.testlocal.module.chatgpt;

import com.example.testlocal.module.chatgpt.MyChatGPTService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.jupiter.api.Assertions.*;
@WebAppConfiguration
@SpringBootTest
class ChatGPTExampleTest {
    @Autowired
    private MyChatGPTService myChatGPTService;

    @Test
    void chat() {
        System.out.println(myChatGPTService.chat("c언어 포인터 알려줘"));
    }
}
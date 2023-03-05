package com.example.testlocal.module.chatgpt.presentation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.jupiter.api.Assertions.*;


@WebAppConfiguration
@SpringBootTest
class ChatGPTMessageControllerTest {

    @Autowired
    private ChatGPTMessageController chatGPTMessageController;

    @Test
    public void 학번으로_채팅_데이터_읽어오기(){
        Assertions.assertThat(chatGPTMessageController.getChatsByStudentId("17011530").getBody().getData().size()).isEqualTo(2);
    }

}
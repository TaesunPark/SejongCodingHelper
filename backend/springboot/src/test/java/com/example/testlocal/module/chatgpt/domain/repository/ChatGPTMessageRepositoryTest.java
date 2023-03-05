package com.example.testlocal.module.chatgpt.domain.repository;

import com.example.testlocal.config.RoleType;
import com.example.testlocal.module.chatgpt.domain.entity.ChatGPT;
import com.example.testlocal.module.chatgpt.domain.entity.UserChatGPTMessage;
import com.example.testlocal.module.user.application.dto.UserDto;
import com.example.testlocal.module.user.domain.entity.User;
import com.example.testlocal.module.user.domain.repository.UserRepository2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@WebAppConfiguration
@SpringBootTest
class ChatGPTMessageRepositoryTest {

    @Autowired
    private ChatGPTMessageRepository chatGPTMessageRepository;
    private User user;
    private ChatGPT chatGPT;
    private UserChatGPTMessage userChatGPTMessage;

    @Autowired
    private ChatGPTRepository chatGPTRepository;

    @Autowired
    private UserRepository2 userRepository;

    @BeforeEach
    public void set(){
        chatGPTMessageRepository.deleteAll();
        chatGPTRepository.deleteAll();
        userRepository.deleteAll();
        UserDto userDTO = new UserDto("17011530", "1234", "박태순", "tovbskvb@sju.ac.kr");
        user = User.builder().email(userDTO.getEmail()).roleType(RoleType.USER).name(userDTO.getName()).studentNumber(userDTO.getStudentNumber()).password(userDTO.getPassword()).build();
        chatGPT = new ChatGPT();
    }

    @Test
    public void getChats(){
        userRepository.save(user);
        chatGPTRepository.save(chatGPT);
        userChatGPTMessage = new UserChatGPTMessage(user, chatGPT, "hi","hi2");
        chatGPTMessageRepository.save(userChatGPTMessage);
        userChatGPTMessage = new UserChatGPTMessage(user, chatGPT, "hi2","hi2");
        chatGPTMessageRepository.save(userChatGPTMessage);

        assertThat(chatGPTMessageRepository.getUserChatGPTMessagesByUser_StudentNumber(user.getStudentNumber()).get().size()).isEqualTo(2);
    }

}
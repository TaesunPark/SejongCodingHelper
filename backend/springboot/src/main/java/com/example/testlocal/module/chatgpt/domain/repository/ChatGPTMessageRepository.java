package com.example.testlocal.module.chatgpt.domain.repository;

import com.example.testlocal.module.chatgpt.domain.entity.UserChatGPTMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatGPTMessageRepository extends JpaRepository<UserChatGPTMessage, Long> {
    public Optional<List<UserChatGPTMessage>> getUserChatGPTMessagesByUser_StudentNumber(String studentNumber);
}

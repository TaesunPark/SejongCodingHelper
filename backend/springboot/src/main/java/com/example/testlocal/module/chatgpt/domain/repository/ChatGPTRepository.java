package com.example.testlocal.module.chatgpt.domain.repository;

import com.example.testlocal.module.chatgpt.domain.entity.ChatGPT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatGPTRepository extends JpaRepository<ChatGPT, Long> {

}

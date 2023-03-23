package com.example.testlocal.module.chatbot.domain.repository;

import com.example.testlocal.module.chatbot.ChatbotTrainDataC;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
@Deprecated
public interface ChatbotTrainDataCRepository extends JpaRepository<ChatbotTrainDataC,Long> {

    List<ChatbotTrainDataC> findTop15ByOrderByCountDesc();

}

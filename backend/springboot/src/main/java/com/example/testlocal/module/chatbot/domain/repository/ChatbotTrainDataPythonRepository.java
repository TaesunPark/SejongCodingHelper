package com.example.testlocal.module.chatbot.domain.repository;

import com.example.testlocal.module.chatbot.ChatbotTrainDataPython;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Deprecated
public interface ChatbotTrainDataPythonRepository extends JpaRepository<ChatbotTrainDataPython, Long> {

    List<ChatbotTrainDataPython> findTop15ByOrderByCountDesc();

}

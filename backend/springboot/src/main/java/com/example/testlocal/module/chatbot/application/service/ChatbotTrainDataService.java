package com.example.testlocal.module.chatbot.application.service;

import com.example.testlocal.module.chatbot.ChatbotTrainDataC;
import com.example.testlocal.module.chatbot.ChatbotTrainDataPython;
import com.example.testlocal.module.chatbot.domain.repository.ChatbotTrainDataCRepository;
import com.example.testlocal.module.chatbot.domain.repository.ChatbotTrainDataPythonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Deprecated
@Service
@RequiredArgsConstructor
public class ChatbotTrainDataService {

    private final ChatbotTrainDataCRepository chatbotTrainDataCRepository;
    private final ChatbotTrainDataPythonRepository chatbotTrainDataPythonRepository;

    public List<ChatbotTrainDataC> findTop10TrainDataC(){
        return chatbotTrainDataCRepository.findTop15ByOrderByCountDesc();
    }

    public List<ChatbotTrainDataPython> findTop10TrainDataPython(){
        return chatbotTrainDataPythonRepository.findTop15ByOrderByCountDesc();
    }
}

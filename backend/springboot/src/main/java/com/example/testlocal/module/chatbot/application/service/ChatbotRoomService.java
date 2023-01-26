package com.example.testlocal.module.chatbot.application.service;

import com.example.testlocal.domain.dto.ChatbotRoomDTO;
import com.example.testlocal.domain.entity.ChatbotRoom;
import com.example.testlocal.core.exception.InvalidUserIdException;
import com.example.testlocal.module.chatbot.domain.repository.ChatbotRoomRepository;
import com.example.testlocal.core.security.JwtTokenProvider;

import com.example.testlocal.module.user.application.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatbotRoomService {

    private final ChatbotRoomRepository repository;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    public ChatbotRoom create(ChatbotRoomDTO roomDTO){
        ChatbotRoom room = new ChatbotRoom(roomDTO, userService);
        return repository.save(room);
    }

    public List<ChatbotRoom> findAllRoomByStudentId(String refreshToken){
        String studentId = jwtTokenProvider.getUserPk(refreshToken);
        return repository.findAllRoomByStudentId(studentId);
    }

    public ChatbotRoom findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new InvalidUserIdException());
    }
}

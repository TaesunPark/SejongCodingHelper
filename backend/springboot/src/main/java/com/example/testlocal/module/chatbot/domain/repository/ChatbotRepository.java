package com.example.testlocal.module.chatbot.domain.repository;

import com.example.testlocal.module.chatbot.domain.entity.Chatbot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Deprecated
@Repository
public interface ChatbotRepository extends JpaRepository<Chatbot, Long> {

    @Query(value = "select * from chatbot where chatbot_room_id = ?1", nativeQuery = true)
    List<Chatbot> findAllByRoomId(Long roomId);
}

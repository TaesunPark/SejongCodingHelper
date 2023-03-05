package com.example.testlocal.module.chatbot.domain.repository;

import com.example.testlocal.module.chatbot.domain.entity.ChatbotRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Deprecated
@Repository
public interface ChatbotRoomRepository extends JpaRepository<ChatbotRoom,Long> {

    @Query(value = "select * from chatbot_room where user_id = " +
            "(select id from user where student_number = ?1) or user2_id = (" +
            "select id from user where student_number = ?1) ", nativeQuery = true)
    List<ChatbotRoom> findAllRoomByStudentId(String studentNumber);

}
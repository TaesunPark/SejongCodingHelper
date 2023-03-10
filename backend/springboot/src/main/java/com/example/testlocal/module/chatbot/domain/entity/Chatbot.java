package com.example.testlocal.module.chatbot.domain.entity;

import com.example.testlocal.domain.dto.ChatbotDTO;
import com.example.testlocal.module.user.application.service.impl.UserService;
import com.example.testlocal.module.user.domain.entity.User;
import com.example.testlocal.module.chatbot.application.service.ChatbotRoomService;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Deprecated
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "chatbot")
@Table(name = "chatbot")
@Builder
public class Chatbot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // chatbot id

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "create_time", nullable = false)
    private Timestamp createTime;

    @Column(name = "recommends", nullable = true)
    private String recommends;

    @ManyToOne
    @JoinColumn(name = "chatbot_room_id", nullable = false)
    private ChatbotRoom room;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public void setRecommends(String recommends) {
        this.recommends = recommends;
    }

    public Chatbot(ChatbotDTO requestDTO, UserService userService, ChatbotRoomService roomService) {
        this.message = requestDTO.getMessage();
        this.createTime = requestDTO.getCreateTime();
        this.user = userService.findById(requestDTO.getUserId());
        this.room = roomService.findById(requestDTO.getChatbotRoomId());
        this.recommends = requestDTO.getRecommends();
    }


}

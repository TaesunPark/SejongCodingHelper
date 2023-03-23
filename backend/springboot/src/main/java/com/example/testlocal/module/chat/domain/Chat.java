package com.example.testlocal.domain.entity;

import com.example.testlocal.module.chat.application.dto.ChatDTO2;

import com.example.testlocal.module.user.application.service.impl.UserService;
import com.example.testlocal.module.user.domain.entity.User;
import com.example.testlocal.module.chat.application.service.RoomService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "chat")
@Table(name = "chat")
@Builder
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // chat id

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "create_time", nullable = false)
    private Timestamp createTime;

    @Column(name = "chat_read_count", nullable = false)
    private Integer chatRead;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Chat(ChatDTO2 requestDTO, RoomService roomService, UserService userService) {
        this.message = requestDTO.getMessage();
        this.createTime = requestDTO.getCreateTime();
        this.chatRead = requestDTO.getChatRead();
        this.room = roomService.findById(requestDTO.getRoomId());
        this.user = userService.findById(requestDTO.getUserId());
    }

}

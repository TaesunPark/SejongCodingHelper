package com.example.testlocal.module.chat.domain;

import com.example.testlocal.module.user.domain.entity.User;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "room_user")
@Table(name = "room_user")
public class RoomUser {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;


}

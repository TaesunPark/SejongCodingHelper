package com.example.testlocal.module.chat.domain;

import com.example.testlocal.module.chat.application.dto.RoomDTO;
import com.example.testlocal.module.user.application.service.impl.UserService;
import com.example.testlocal.module.user.domain.entity.User;
import com.example.testlocal.util.DateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "room")
@Table(name = "room")
@Builder
public class Room extends DateTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Room id

    @Column(name = "title", nullable = false)
    private String title;

    @OneToMany(fetch = FetchType.LAZY)
    private List<RoomUser> roomUserList;

    public Room(RoomDTO requestDTO) {
        this.title = requestDTO.getTitle();
        this.roomUserList = new ArrayList<>();
    }

}

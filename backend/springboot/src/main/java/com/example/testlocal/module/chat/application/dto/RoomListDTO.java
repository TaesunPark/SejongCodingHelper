package com.example.testlocal.module.chat.application.dto;

import com.example.testlocal.module.chat.domain.Room;
import com.example.testlocal.module.chat.domain.RoomUser;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RoomListDTO {

    ArrayList<RoomDTO> roomDTOs;

    public RoomListDTO(List<RoomUser> roomList){
        roomDTOs = new ArrayList<>();

        for (RoomUser roomUser : roomList){
            roomDTOs.add(new RoomDTO(roomUser.getRoom().getTitle()));
        }
    }
}

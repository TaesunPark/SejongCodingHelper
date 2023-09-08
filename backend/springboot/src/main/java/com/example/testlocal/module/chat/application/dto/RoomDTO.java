package com.example.testlocal.module.chat.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RoomDTO {

    private String title;
    public RoomDTO(String title) {
        this.title = title;
    }
}

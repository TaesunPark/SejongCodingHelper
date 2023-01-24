package com.example.testlocal.module.user.application.dto;

import com.example.testlocal.module.user.domain.entity.User;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String studentNumber;
    private String password;
    private String name;
    private String email;
}

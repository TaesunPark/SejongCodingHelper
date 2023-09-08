package com.example.testlocal.module.user.application.dto;

import com.example.testlocal.module.user.domain.entity.User;
import lombok.*;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String studentNumber;
    private String password;
    private String name;
    private String email;

    public UserDto(Optional<User> user) {
        studentNumber = user.get().getStudentNumber();
        password = user.get().getPassword();
        name = user.get().getName();
        email = user.get().getEmail();
    }
}

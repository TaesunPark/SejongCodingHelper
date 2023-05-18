package com.example.testlocal.module.user.presentation.controller;

import com.example.testlocal.module.user.application.dto.UserDto;
import com.example.testlocal.module.user.domain.entity.User;
import com.example.testlocal.module.user.domain.repository.UserRepository2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.servlet.http.HttpServletResponse;

@WebAppConfiguration
@SpringBootTest
class LoginControllerTest {
    @Autowired
    private LoginContoller loginContoller;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository2 userRepository;

    @Autowired
    private HttpServletResponse httpServletResponse;

    @BeforeEach
    void init(){
        userRepository.deleteAll();
    }

    @Test
    void loginUser() {
        UserDto userDTO = new UserDto("17011526", passwordEncoder.encode("1234"), "박태순", "tovbskvb@sju.ac.kr");
        User user = User.builder().email(userDTO.getEmail()).name(userDTO.getName()).studentNumber(userDTO.getStudentNumber()).password(userDTO.getPassword()).build();
        userRepository.save(user);
    }

    @Test
    void refreshLoginToken() {
    }

    @Test
    void logout() {
    }
}
package com.example.testlocal.module.user.application.service.impl;

import com.example.testlocal.module.user.application.dto.UserDto;
import com.example.testlocal.module.user.application.dto.response.EmailCheckResponse;
import com.example.testlocal.module.user.application.service.UserCheckService;
import com.example.testlocal.module.user.domain.entity.User;
import com.example.testlocal.module.user.domain.repository.UserRepository2;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@WebAppConfiguration
@SpringBootTest
class UserCheckServiceTest {

    @Autowired
    private UserCheckServiceImpl userCheckService;

    @Autowired
    private UserRepository2 userRepository;

    @BeforeEach
    public void init(){
        userRepository.deleteAll();
    }

    @DisplayName("중복 이메일 테스트 성공")
    @Test
    public void isOverlapEmail(){
        Boolean isOverlap = userCheckService.isOverlapEmail("tovbskvb@daum.net");
        assertThat(isOverlap).isEqualTo(true);
    }

    @DisplayName("중복 이메일 테스트 실패")
    @Test
    public void isOverlapEmail_FAIL(){
        UserDto userDTO = new UserDto("17011526", "1234", "박태순", "tovbskvb@sju.ac.kr");
        User user = User.builder().email(userDTO.getEmail()).name(userDTO.getName()).studentNumber(userDTO.getStudentNumber()).password(userDTO.getPassword()).build();
        userRepository.save(user);
        Boolean isOverlap = userCheckService.isOverlapEmail("tovbskvb@sju.ac.kr");
        assertThat(isOverlap).isEqualTo(false);

    }
}
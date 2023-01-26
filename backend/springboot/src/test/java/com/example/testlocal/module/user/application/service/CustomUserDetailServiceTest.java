package com.example.testlocal.module.user.application.service;

import com.example.testlocal.module.user.application.service.impl.CustomUserDetailService;
import com.example.testlocal.module.user.domain.entity.User;
import com.example.testlocal.module.user.domain.repository.UserRepository2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@WebAppConfiguration
@SpringBootTest
class CustomUserDetailServiceTest {
    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private UserRepository2 userRepository;

    @DisplayName("UserDetail 반환하는 비교를 한다.")
    @Test
    void UserDetail_반환() {
        User user = User.builder().id(1).email("tovbskvb@daum.net").name("박태순").password("1234567").studentNumber("17011526").build();
        userRepository.save(user);
        UserDetails userDetails = customUserDetailService.loadUserByUsername("17011526");
        assertThat(userDetails.getUsername()).isEqualTo("박태순");
    }

}
package com.example.testlocal.module.user.application.service;

import com.example.testlocal.core.exception.login.InvalidCredentialsException;
import com.example.testlocal.core.security.CustomUserDetailService;
import com.example.testlocal.core.security.CustomUserDetails;
import com.example.testlocal.core.security.jwt.JwtTokenProvider;
import com.example.testlocal.module.user.domain.entity.Role;
import com.example.testlocal.module.user.domain.entity.RoleName;
import com.example.testlocal.module.user.domain.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@WebMvcTest(CustomUserDetailService.class)
class CustomUserDetailServiceUnitTest {

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private UserDetailsService userDetailsService;
    @Mock
    private CustomUserDetailService customUserDetailService;

    private User user;

    @BeforeEach
    void set(){
        // given
        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setRole(RoleName.ROLE_USER);
        roles.add(role);
        user = User.builder().roles(roles).email("tovbskvb@daum.net").name("박태순").password("1234567").studentNumber("17011526").build();
    }

    @DisplayName("올바른 로그인 성공, 200")
    @Test
    void 로그인_성공_200() {
        UserDetails userDetails = new CustomUserDetails(user);
        Mockito.when(customUserDetailService.loadUserByUsername(user.getStudentNumber())).thenReturn(userDetails);
        //when
        UserDetails findUser = customUserDetailService.loadUserByUsername(user.getStudentNumber());
        //then
        assertThat(findUser.getUsername()).isEqualTo(user.getStudentNumber());
    }

    @DisplayName("해당하는 학번 없을 때 401 출력")
    @Test
    void 존재하지_않은_학번() {
        // 잘못된 임의 학번 세팅 -> null 반환
        Mockito.when(customUserDetailService.loadUserByUsername("17011522")).thenThrow(new InvalidCredentialsException());

        //when
        Assertions.assertThrows(InvalidCredentialsException.class, () -> {
            customUserDetailService.loadUserByUsername("17011522");
        });
    }


}
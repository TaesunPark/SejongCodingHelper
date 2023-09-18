package com.example.testlocal.module.user.presentation.controller;

import com.example.testlocal.config.SecurityConfig;
import com.example.testlocal.module.user.application.dto.SendEmailRequest;
import com.example.testlocal.module.user.application.service.UserCheckService;
import com.example.testlocal.module.user.application.service.impl.EmailService;
import com.example.testlocal.module.user.application.service.impl.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(value = SignupController.class,
        excludeFilters = {
        @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = SecurityConfig.class
        )
})
class SignupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    UserService userService;
    @MockBean
    EmailService emailService;
    @MockBean
    UserCheckService userCheckService;

    @DisplayName("[API][POST] email send")
    @WithMockUser(username = "테스트_최고관리자", roles = {"SUPER"})
    @Test
    void sendSejongEmail() throws Exception {
        // given
        SendEmailRequest sendEmailRequest = new SendEmailRequest();
        sendEmailRequest.setEmail("tovbskvb@daum.net");

        // when
        mockMvc.perform(
                post("/api/signup/send-email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8") // Set UTF-8 encoding
                        .content(objectMapper.writeValueAsBytes(sendEmailRequest)).with(SecurityMockMvcRequestPostProcessors.csrf())
            )
                .andExpect(status().isOk());


        // then
    }

    @Test
    void checkEmailAuthCode() {
    }

    @Test
    void checkEmailOverlap() {
    }

    @Test
    void completeUserSignup() {
    }
}
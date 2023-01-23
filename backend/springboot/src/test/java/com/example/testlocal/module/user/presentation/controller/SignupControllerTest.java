package com.example.testlocal.module.user.presentation.controller;

import com.example.testlocal.core.dto.SuccessCode;
import com.example.testlocal.core.dto.SuccessResponse;
import com.example.testlocal.module.user.application.dto.SendEmailRequest;
import com.example.testlocal.module.user.application.dto.request.EmailCodeRequest;
import com.example.testlocal.module.user.application.dto.response.EmailCodeResponse;
import com.example.testlocal.module.user.application.dto.response.SendEmailResponse;
import com.example.testlocal.module.user.domain.repository.EmailCertificationDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.mail.MessagingException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@WebAppConfiguration
@SpringBootTest
class SignupControllerTest {
    @Autowired
    private SignupController signupController;

    @Autowired
    private EmailCertificationDao emailCertificationDao;

    @DisplayName("이메일 보내기 컨트롤러 테스트")
    @Test
    void sendSejongEmail() throws MessagingException {
        SendEmailRequest sendEmailRequest = new SendEmailRequest();
        sendEmailRequest.setEmail("tovbskvb");
        ResponseEntity<SuccessResponse<SendEmailResponse>> result = signupController.sendSejongEmail(sendEmailRequest, null);
        assertThat(HttpStatus.OK).isEqualTo(result.getStatusCode());
        assertThat(result.getBody().getMessage()).isEqualTo(SuccessCode.EMAIL_SUCCESS.getMessage());
    }

    @DisplayName("이메일 코드 검증 컨트롤러 테스트")
    @Test
    void checkEmailAuthCode() {
        emailCertificationDao.createCodeCertification("tovbskvb@sju.ac.kr", "123456");
        EmailCodeRequest emailCodeRequest = new EmailCodeRequest();
        emailCodeRequest.setEmail("tovbskvb@sju.ac.kr");
        emailCodeRequest.setAuthCode("123456");
        ResponseEntity<SuccessResponse<EmailCodeResponse>> result = signupController.checkEmailAuthCode(emailCodeRequest, null);
        assertThat(HttpStatus.OK).isEqualTo(result.getStatusCode());
        assertThat(result.getBody().getMessage()).isEqualTo(SuccessCode.EMAIL_CODE_SUCCESS.getMessage());
    }

    @Test
    void checkEmailOverlap() {

    }

    @Test
    void completeUserSignup() {

    }

    @Test
    void checkIdOverlap() {

    }
}
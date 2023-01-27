package com.example.testlocal.module.user.application.service;


import com.example.testlocal.module.user.application.dto.SendEmailRequest;
import com.example.testlocal.module.user.application.dto.request.EmailCodeRequest;
import com.example.testlocal.module.user.application.dto.response.EmailCodeResponse;
import com.example.testlocal.module.user.application.dto.response.SendEmailResponse;
import com.example.testlocal.module.user.application.service.impl.EmailService;
import com.example.testlocal.module.user.domain.repository.EmailCertificationDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.mail.MessagingException;

import static org.assertj.core.api.Assertions.assertThat;

@WebAppConfiguration
@SpringBootTest
class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Autowired
    private EmailCertificationDao emailCertificationDao;

    private SendEmailRequest sendEmailRequest;
    private EmailCodeRequest emailCodeRequest;

    @BeforeEach
    public void setValue(){
        sendEmailRequest = new SendEmailRequest();
        emailCodeRequest = new EmailCodeRequest();
        sendEmailRequest.setEmail("tovbskvb");
        emailCodeRequest.setEmail("tovbskvb");
        emailCodeRequest.setAuthCode("12345");
    }

    @Test
    void sendSejongEmail() throws MessagingException {
        SendEmailResponse sendEmailResponse = emailService.sendSejongEmail(sendEmailRequest, null);
        assertThat(sendEmailResponse.getEmail()).isEqualTo(sendEmailRequest.getEmail());
    }

    @Test
    void checkEmailAuthCode() {
        emailCertificationDao.createCodeCertification(emailCodeRequest.getEmail(), "12345");
        EmailCodeResponse emailCodeResponse = emailService.checkEmailAuthCode(emailCodeRequest, null);
        assertThat(emailCodeResponse.getAuthCode()).isEqualTo("12345");
    }
}
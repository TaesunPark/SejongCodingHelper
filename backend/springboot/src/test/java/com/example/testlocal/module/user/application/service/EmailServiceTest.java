package com.example.testlocal.module.user.application.service;


import com.example.testlocal.config.mail.EmailTestConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith({SpringExtension.class})
@Import({EmailTestConfig.class})
class EmailServiceTest {

    @InjectMocks
    private EmailService emailService;

    @Autowired
    private JavaMailSender MyMailService;

    @DisplayName("이메일 보내는 테스트 결과 값은 메일 확인")
    @Test
    public void sendEmail() throws MessagingException {
        String email = "tovbskvb@sju.ac.kr";

        String content = "test";

        // 메일 보내기
        MimeMessage message = MyMailService.createMimeMessage();

        MimeMessageHelper h = new MimeMessageHelper(message,"UTF-8");

        h.setFrom("sjcodinghelper@naver.com");
        h.setTo(email);
        h.setSubject("Sejong Coding Helper 회원가입 인증 메일");
        h.setText(content);
        h.setSentDate(new Date());
        MyMailService.send(h.getMimeMessage());
    }

    @DisplayName("랜덤 값 확인 테스트")
    @Test
    public void random() throws MessagingException {
        String value1 = emailService.randomCode();
        assertThat(value1).isNotSameAs(emailService.randomCode());
    }

}
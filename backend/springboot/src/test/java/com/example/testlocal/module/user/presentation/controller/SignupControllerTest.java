package com.example.testlocal.module.user.presentation.controller;

import com.example.testlocal.core.dto.SuccessCode;
import com.example.testlocal.core.dto.SuccessResponse;
import com.example.testlocal.domain.dto.UserDTO2;
import com.example.testlocal.module.user.application.dto.SendEmailRequest;
import com.example.testlocal.module.user.application.dto.request.EmailCheckRequest;
import com.example.testlocal.module.user.application.dto.request.EmailCodeRequest;
import com.example.testlocal.module.user.application.dto.response.EmailCheckResponse;
import com.example.testlocal.module.user.application.dto.response.EmailCodeResponse;
import com.example.testlocal.module.user.application.dto.response.SendEmailResponse;
import com.example.testlocal.module.user.application.service.UserService;
import com.example.testlocal.module.user.domain.entity.User;
import com.example.testlocal.module.user.domain.repository.EmailCertificationDao;
import com.example.testlocal.module.user.domain.repository.UserRepository2;
import org.junit.Before;
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

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@WebAppConfiguration
@SpringBootTest
class SignupControllerTest {
    @Autowired
    private SignupController signupController;

    @Autowired
    private EmailCertificationDao emailCertificationDao;

    @Autowired
    private UserRepository2 userRepository;

    @BeforeEach
    void set(){
        userRepository.deleteAll();
    }

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

    @DisplayName("이메일 중복 확인 성공 컨트롤러 테스트")
    @Test
    void checkEmailOverlap() {
        EmailCheckRequest checkEmailRequest = new EmailCheckRequest();
        checkEmailRequest.setEmail("tovbskvb");
        ResponseEntity<SuccessResponse<EmailCheckResponse>> result = signupController.checkEmailOverlap(checkEmailRequest);
        assertThat(HttpStatus.OK).isEqualTo(result.getStatusCode());
        assertThat(result.getBody().getMessage()).isEqualTo(SuccessCode.EMAIL_DUPLICATED_CODE_SUCCESS.getMessage());
    }

    @DisplayName("이메일 중복 컨트롤러 테스트, 중복 감지")
    @Test
    void checkDuplicatedEmailOverlap() {
        UserDTO2 userDTO2 = new UserDTO2("17011526", "1234", "박태순", "tovbskvb@sju.ac.kr");
        userRepository.save(userDTO2.toEntity());
        EmailCheckRequest checkEmailRequest = new EmailCheckRequest();
        ResponseEntity<SuccessResponse<EmailCheckResponse>> result = signupController.checkEmailOverlap(checkEmailRequest);
        assertThat(HttpStatus.OK).isEqualTo(result.getStatusCode());
        assertThat(result.getBody().getMessage()).isEqualTo(SuccessCode.EMAIL_DUPLICATED_CODE_SUCCESS.getMessage());
    }

    @DisplayName("회원가입 완료 컨트롤러 테스트, 성공")
    @Test
    void completeUserSignup() {
        HashMap map = new HashMap();
        map.put("studentNumber", "17011526");
        map.put("pwd", "1234");
        map.put("name","박태순");
        map.put("email","tovbskvb@sju.ac.kr");
        String value = signupController.completeUserSignup(map);
        assertThat(value).isEqualTo("accepted");
    }

    @DisplayName("회원가입 완료 컨트롤러 테스트, 실패")
    @Test
    void completeSignup_FAIL() {
        UserDTO2 userDTO2 = new UserDTO2("17011527", "1234", "박태순", "tovbskvb@sju.ac.kr");
        User user = new User(userDTO2);
        userRepository.save(user);
        HashMap map = new HashMap();
        map.put("studentNumber", "17011527");
        map.put("pwd", "1234");
        map.put("name","박태순");
        map.put("email","tovbskvb@sju.ac.kr");
        String value = signupController.completeUserSignup(map);
        assertThat(value).isEqualTo("denied");
    }


}
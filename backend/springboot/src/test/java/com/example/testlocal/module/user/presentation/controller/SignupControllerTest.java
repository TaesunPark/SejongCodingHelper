package com.example.testlocal.module.user.presentation.controller;

import com.example.testlocal.config.RoleType;
import com.example.testlocal.core.dto.SuccessCode;
import com.example.testlocal.core.dto.SuccessResponse;
import com.example.testlocal.module.user.application.dto.SendEmailRequest;
import com.example.testlocal.module.user.application.dto.UserDto;
import com.example.testlocal.module.user.application.dto.request.EmailCheckRequest;
import com.example.testlocal.module.user.application.dto.request.EmailCodeRequest;
import com.example.testlocal.module.user.application.dto.request.UserInfoRequest;
import com.example.testlocal.module.user.application.dto.response.EmailCheckResponse;
import com.example.testlocal.module.user.application.dto.response.EmailCodeResponse;
import com.example.testlocal.module.user.application.dto.response.SendEmailResponse;
import com.example.testlocal.module.user.application.dto.response.UserInfoResponse;
import com.example.testlocal.module.user.domain.entity.User;
import com.example.testlocal.module.user.domain.repository.EmailCertificationDao;
import com.example.testlocal.module.user.domain.repository.UserRepository2;
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
        UserDto userDTO = new UserDto("17011526", "1234", "박태순", "tovbskvb@sju.ac.kr");
        User user = User.builder().email(userDTO.getEmail()).roleType(RoleType.USER).name(userDTO.getName()).studentNumber(userDTO.getStudentNumber()).password(userDTO.getPassword()).build();
        userRepository.save(user);
        EmailCheckRequest checkEmailRequest = new EmailCheckRequest();
        ResponseEntity<SuccessResponse<EmailCheckResponse>> result = signupController.checkEmailOverlap(checkEmailRequest);
        assertThat(HttpStatus.OK).isEqualTo(result.getStatusCode());
        assertThat(result.getBody().getMessage()).isEqualTo(SuccessCode.EMAIL_DUPLICATED_CODE_SUCCESS.getMessage());
    }

    @DisplayName("회원가입 완료 컨트롤러 테스트, 성공")
    @Test
    void completeUserSignup() {
        UserInfoRequest userInfoRequest = new UserInfoRequest();
        userInfoRequest.setStudentNumber("17011526");
        userInfoRequest.setEmail("tovbskvb@sju.ac.kr");
        userInfoRequest.setName("박태순");
        userInfoRequest.setPwd("1234");
        ResponseEntity<SuccessResponse<UserInfoResponse>> result = signupController.completeUserSignup(userInfoRequest);
        assertThat(HttpStatus.OK).isEqualTo(result.getStatusCode());
        assertThat(result.getBody().getMessage()).isEqualTo(SuccessCode.SIGNUP_SUCCESS.getMessage());
    }

    @DisplayName("회원가입 완료 컨트롤러 테스트, 실패")
    @Test
    void completeSignup_FAIL() {
        UserDto userDTO = new UserDto("17011526", "1234", "박태순", "tovbskvb@sju.ac.kr");
        User user = User.builder().email(userDTO.getEmail()).roleType(RoleType.USER).name(userDTO.getName()).studentNumber(userDTO.getStudentNumber()).password(userDTO.getPassword()).build();
        userRepository.save(user);
        HashMap map = new HashMap();
        UserInfoRequest userInfoRequest = new UserInfoRequest();
        userInfoRequest.setStudentNumber("17011526");
        userInfoRequest.setEmail("tovbskvb@sju.ac.kr");
        userInfoRequest.setName("박태순");
        userInfoRequest.setPwd("1234");
        ResponseEntity<SuccessResponse<UserInfoResponse>> result = signupController.completeUserSignup(userInfoRequest);
        assertThat(HttpStatus.OK).isEqualTo(result.getStatusCode());
        assertThat(result.getBody().getMessage()).isEqualTo(SuccessCode.SIGNUP_SUCCESS.getMessage());
    }


}
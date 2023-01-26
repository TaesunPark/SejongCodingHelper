package com.example.testlocal.module.user.presentation.controller;

import com.example.testlocal.config.Constants;
import com.example.testlocal.core.dto.SuccessCode;
import com.example.testlocal.core.dto.SuccessResponse;
import com.example.testlocal.module.user.application.dto.SendEmailRequest;
import com.example.testlocal.module.user.application.dto.request.EmailCheckRequest;
import com.example.testlocal.module.user.application.dto.request.EmailCodeRequest;
import com.example.testlocal.module.user.application.dto.request.UserInfoRequest;
import com.example.testlocal.module.user.application.dto.response.EmailCheckResponse;
import com.example.testlocal.module.user.application.dto.response.EmailCodeResponse;
import com.example.testlocal.module.user.application.dto.response.SendEmailResponse;
import com.example.testlocal.module.user.application.dto.response.UserInfoResponse;
import com.example.testlocal.module.user.application.service.UserCheckService;
import com.example.testlocal.module.user.application.service.impl.EmailService;
import com.example.testlocal.module.user.application.service.impl.UserService;
import com.example.testlocal.module.chatbot.application.service.ChatbotRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@CrossOrigin(origins = Constants.URL , allowCredentials = "true")
public class SignupController {

    private final JavaMailSender javaMailSender;
    private final UserService userService;
    private final ChatbotRoomService chatbotRoomService;
    private final EmailService emailService;
    private final UserCheckService userCheckServiceImpl;

    @Value("${spring.mail.username}")
    private String from;

    @PostMapping("/email")
    public ResponseEntity<SuccessResponse<SendEmailResponse>> sendSejongEmail(@RequestBody SendEmailRequest sendEmailRequest, HttpServletRequest request) throws MessagingException {
        return SuccessResponse.success(SuccessCode.EMAIL_SUCCESS, emailService.sendSejongEmail(sendEmailRequest, request));
    }

    @PostMapping("/emailAuthCode")
    public ResponseEntity<SuccessResponse<EmailCodeResponse>> checkEmailAuthCode(@RequestBody EmailCodeRequest emailCodeRequest, HttpServletRequest request) {
        return SuccessResponse.success(SuccessCode.EMAIL_CODE_SUCCESS, emailService.checkEmailAuthCode(emailCodeRequest, request));
    }

    @PostMapping("/checkEmailOverlap")
    public ResponseEntity<SuccessResponse<EmailCheckResponse>> checkEmailOverlap(@RequestBody EmailCheckRequest checkEmailRequest) {
        String email = checkEmailRequest.getEmail() + "@sju.ac.kr";
        Boolean isPresented = userCheckServiceImpl.isOverlapEmail(email);
        return SuccessResponse.success(SuccessCode.EMAIL_DUPLICATED_CODE_SUCCESS, EmailCheckResponse.of(email, isPresented));
    }

    @PostMapping("/completeUserSignup")
    public ResponseEntity<SuccessResponse<UserInfoResponse>> completeUserSignup(@RequestBody UserInfoRequest userInfoRequest) {
        //chatbotRoomService.create(new ChatbotRoomDTO(id,4L,"C", "0"));
        //chatbotRoomService.create(new ChatbotRoomDTO(id,4L,"P", "0"));
        return SuccessResponse.success(SuccessCode.SIGNUP_SUCCESS, userService.signUp(userInfoRequest));
    }

}

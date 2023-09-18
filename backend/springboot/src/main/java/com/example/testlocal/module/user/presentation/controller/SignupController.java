package com.example.testlocal.module.user.presentation.controller;

import com.example.testlocal.util.Constants;
import com.example.testlocal.core.dto.SuccessCode;
import com.example.testlocal.core.dto.SuccessResponse;
import com.example.testlocal.core.exception.ConflictException;
import com.example.testlocal.core.exception.ErrorCode;
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
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@CrossOrigin(origins = Constants.URL , allowCredentials = "true")
@RequestMapping("/api/signup")
public class SignupController {

    private final UserService userService;
    private final EmailService emailService;
    private final UserCheckService userCheckServiceImpl;

    // 사용자에게 확인 코드 이메일 보내기
    @PostMapping("/send-email")
    public ResponseEntity<SuccessResponse<SendEmailResponse>> sendSejongEmail(@RequestBody SendEmailRequest sendEmailRequest, HttpServletRequest request) throws MessagingException {
        return SuccessResponse.success(SuccessCode.EMAIL_SUCCESS, emailService.sendSejongEmail(sendEmailRequest, request));
    }

    // 검증 코드 확인하기
    @PostMapping("/confirm-email-code")
    public ResponseEntity<SuccessResponse<EmailCodeResponse>> checkEmailAuthCode(@RequestBody EmailCodeRequest emailCodeRequest, HttpServletRequest request) {
        return SuccessResponse.success(SuccessCode.EMAIL_CODE_SUCCESS, emailService.checkEmailAuthCode(emailCodeRequest, request));
    }

    // 이메일 중복 검사하기
    @PostMapping("/check-email-duplicate")
    public ResponseEntity<SuccessResponse<EmailCheckResponse>> checkEmailOverlap(@RequestBody EmailCheckRequest checkEmailRequest) {
        String email = checkEmailRequest.getEmail() + "@sju.ac.kr";
        Boolean isPresented = userCheckServiceImpl.isOverlapEmail(email);

        if (!isPresented){
            throw new ConflictException(String.format("이미 존재하는 이메일 (%s) 입니다", checkEmailRequest.getEmail()),
                    ErrorCode.CONFLICT_EMAIL_EXCEPTION);
        }

        return SuccessResponse.success(SuccessCode.EMAIL_DUPLICATED_CODE_SUCCESS, new EmailCheckResponse(email));
    }

    // 유저 회원 가입
    @PostMapping()
    public ResponseEntity<SuccessResponse<UserInfoResponse>> completeUserSignup(@RequestBody UserInfoRequest userInfoRequest) {
        return SuccessResponse.success(SuccessCode.SIGNUP_SUCCESS, userService.signUp(userInfoRequest));
    }

}

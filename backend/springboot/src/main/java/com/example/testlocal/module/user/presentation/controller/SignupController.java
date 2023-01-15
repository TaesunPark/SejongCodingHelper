package com.example.testlocal.module.user.presentation.controller;

import com.example.testlocal.config.Constants;
import com.example.testlocal.core.dto.SuccessCode;
import com.example.testlocal.core.dto.SuccessResponse;
import com.example.testlocal.core.dto.SuccessStatusCode;
import com.example.testlocal.domain.dto.ChatbotRoomDTO;
import com.example.testlocal.domain.dto.UserDTO2;
import com.example.testlocal.module.user.application.dto.SendEmailRequest;
import com.example.testlocal.module.user.application.dto.response.SendEmailResponse;
import com.example.testlocal.module.user.application.service.EmailService;
import com.example.testlocal.module.user.application.service.UserService;
import com.example.testlocal.module.chatbot.application.service.ChatbotRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;
import java.util.Random;

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

    @Value("${spring.mail.username}")
    private String from;

    @PostMapping("/email")
    public ResponseEntity<SuccessResponse<SendEmailResponse>> sendSejongEmail(@RequestBody SendEmailRequest sendEmailRequest, HttpServletRequest request) throws MessagingException {
        return SuccessResponse.success(SuccessCode.EMAIL_SUCCESS, emailService.sendSejongEmail(sendEmailRequest, request));
    }

    @PostMapping("/checkEmailAuthCode")
    public String checkEmailAuthCode(@RequestBody Map<String, String> map, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String authCode = (String) session.getAttribute("authCode");
        String inputedAuthCode = map.get("authCode");
        System.out.println(authCode + "," + inputedAuthCode);

        if (authCode.equals(inputedAuthCode))
            return "accepted";
        else
            return "fail";
    }

    @PostMapping("/completeUserSignup")
    public String completeUserSignup(@RequestBody Map<String, String> map) {

        //학번 중복 확인
        if (userService.isOverlapStudentNumber(map.get("studentNumber"))) {
            return "denied";
        }
        // db에 저장하는 구문
        long id = userService.signUp(new UserDTO2(map.get("studentNumber"), map.get("pwd"), map.get("name"), map.get("email")));
        System.out.println(id);
        chatbotRoomService.create(new ChatbotRoomDTO(id,4L,"C", "0"));
        chatbotRoomService.create(new ChatbotRoomDTO(id,4L,"P", "0"));
        return "accepted";
    }

    @PostMapping("/checkEmailOverlap")
    public String checkEmailOverlap(@RequestBody Map<String, String> map) {
        if (userService.isOverlapEmail(map.get("email") + "@sju.ac.kr"))
            return "denied";
        return "accepted";
    }

    @PostMapping("/checkIdOverlap")
    public void checkIdOverlap(@RequestBody Map<String, String> map) {

    }

}

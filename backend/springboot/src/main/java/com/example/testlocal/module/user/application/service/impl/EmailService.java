package com.example.testlocal.module.user.application.service.impl;

import com.example.testlocal.core.dto.SuccessResponse;
import com.example.testlocal.core.exception.ErrorCode;
import com.example.testlocal.core.exception.NotFoundException;
import com.example.testlocal.module.user.application.dto.SendEmailRequest;
import com.example.testlocal.module.user.application.dto.request.EmailCodeRequest;
import com.example.testlocal.module.user.application.dto.response.EmailCodeResponse;
import com.example.testlocal.module.user.application.dto.response.SendEmailResponse;
import com.example.testlocal.module.user.domain.repository.EmailCertificationDao;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class EmailService{
    private final JavaMailSender javaMailService;
    private final EmailCertificationDao emailCertificationDao;

    @Value("${spring.mail.username}")
    private String from;

    public SendEmailResponse sendSejongEmail(SendEmailRequest sendEmailRequest, HttpServletRequest request) throws MessagingException {
        String authCode = getAuthCode();
        sendEmail(sendEmailRequest.getEmail(), authCode);
        createCodeInRedis(sendEmailRequest.getEmail() + "@sju.ac.kr", authCode);
        //setSession(request, authCode);
        return SendEmailResponse.of(sendEmailRequest.getEmail());
    }

    public EmailCodeResponse checkEmailAuthCode(EmailCodeRequest emailCodeRequest, HttpServletRequest request){
//        ???????????? ?????? ?????? ?????? ??????
//        HttpSession session = request.getSession();
//        String authCode = (String) session.getAttribute("authCode");
        String authCode = emailCertificationDao.getCodeCertification(emailCodeRequest.getEmail());
        String inputedAuthCode = emailCodeRequest.getAuthCode();

        if (!authCode.equals(inputedAuthCode)) {
            throw new NotFoundException(String.format("???????????? ?????? ?????? ?????? (%s) ?????????", emailCodeRequest.getAuthCode()),
                    ErrorCode.NOT_FOUND_AUTHCODE__EXCEPTION);
        }

        return new EmailCodeResponse(authCode);
    }

    public void sendEmail(String email, String authCode) throws MessagingException {
        email += "@sju.ac.kr";

        String content = "???????????????. Sejong Coding Helper?????????.\n" + "??????????????? ????????? ????????? ????????? ?????? ?????? ????????? ??????????????????.\n" +
                "?????? ?????? : " + authCode +"\n???????????????.";

        // ?????? ?????????
        MimeMessage message = javaMailService.createMimeMessage();
        MimeMessageHelper h = new MimeMessageHelper(message,"UTF-8");

        h.setFrom(from);
        h.setTo(email);
        h.setSubject("Sejong Coding Helper ???????????? ?????? ??????");
        h.setText(content);
        h.setSentDate(new Date());

        // ????????? ??????, ?????? Mail
        javaMailService.send(h.getMimeMessage());
    }

    public void createCodeInRedis(String email, String authCode){
        emailCertificationDao.createCodeCertification(email, authCode);
    }

    public void setSession(HttpServletRequest request, String authCode){
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(60 * 10);  //10???
        session.setAttribute("authCode", authCode);
    }

    public String randomCode(){
        return String.valueOf((int)(Math.random() * (999999 - 100000 + 1)) + 100000);
    }

    //???????????? ?????? ??????
    private String getAuthCode() {
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        int num = 0;

        while (buffer.length() < 6) {
            num = random.nextInt(10);
            buffer.append(num);
        }

        return buffer.toString();
    }

}


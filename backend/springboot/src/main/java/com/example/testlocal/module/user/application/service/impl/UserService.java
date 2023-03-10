package com.example.testlocal.module.user.application.service.impl;

import com.example.testlocal.config.RoleType;
import com.example.testlocal.core.exception.ConflictException;
import com.example.testlocal.core.exception.ErrorCode;
import com.example.testlocal.core.exception.InvalidUserIdException;
import com.example.testlocal.core.exception.UnauthorizedException;
import com.example.testlocal.core.security.JwtTokenProvider;
import com.example.testlocal.module.user.application.dto.UserDto;
import com.example.testlocal.module.user.application.dto.request.UserInfoRequest;
import com.example.testlocal.module.user.application.dto.response.EmailCheckResponse;
import com.example.testlocal.module.user.application.dto.response.UserInfoResponse;
import com.example.testlocal.module.user.application.service.UserCheckService;
import com.example.testlocal.module.user.domain.entity.User;
import com.example.testlocal.module.user.domain.repository.UserRepository2;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;


@RequiredArgsConstructor
@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository2 userRepository2;
    private final JavaMailSender javaMailSender;

    private final UserCheckService userCheckServiceImpl;

    public User create(UserDto requestDTO) {
        User user = User.builder().email(requestDTO.getEmail()).name(requestDTO.getName()).password(requestDTO.getPassword()).studentNumber(requestDTO.getStudentNumber()).roleType(RoleType.USER).build();
        return userRepository2.save(user);
    }

    public User findById(Long id) {
        return userRepository2.findById(id).orElseThrow(() -> new InvalidUserIdException());
    }

    @Transactional
    public UserInfoResponse signUp(UserInfoRequest userInfoRequest) {

        // ???????????? ?????? ??????
        if (!userInfoRequest.getVerifedPwd().equals(userInfoRequest.getPwd())){
            throw new UnauthorizedException(String.format("??????????????? ???????????? ?????? ?????? ????????????."), ErrorCode.UNAUTHORIZED_EXCEPTION);
        }

        // pw??? ??????????????? ??????
        userInfoRequest.setPwd(passwordEncoder.encode(userInfoRequest.getPwd()));

        // ????????? ?????? ??????
        Boolean isOverlapEmail = userCheckServiceImpl.isOverlapEmail(userInfoRequest.getEmail());

        if (!isOverlapEmail){
            throw new ConflictException(String.format("?????? ???????????? ????????? (%s) ?????????", userInfoRequest.getEmail()),
                    ErrorCode.CONFLICT_EMAIL_EXCEPTION);
        }

        // ?????? ?????? ??????
        Boolean isOverlapStudentNumber = userCheckServiceImpl.isOverlapStudentNumber(userInfoRequest.getStudentNumber());

        if (!isOverlapStudentNumber){
            throw new ConflictException(String.format("?????? ???????????? ?????? (%s) ?????????", userInfoRequest.getStudentNumber()),
                    ErrorCode.CONFLICT_STUDENT_NUMBER_EXCEPTION);
        }

        return UserInfoResponse.of(userInfoRequest.getStudentNumber(), true, "???????????? ?????????????????????.");
    }

    public Map<String, String> login(Map<String, String> user) {

        String accessToken = "";
        String refreshToken = "";

        // id??????
        User checkedUser = userRepository2.findByStudentNumber(user.get("id"))
                .orElseThrow(() -> new IllegalArgumentException("id??? ???????????? ????????????."));

        // ?????? ??????
        if (!passwordEncoder.matches(user.get("pwd"), checkedUser.getPassword())) {
            throw new IllegalArgumentException("????????? ?????? ???????????????.");
        }

        accessToken = jwtTokenProvider.createToken(checkedUser.getStudentNumber(), 10L);
        refreshToken = jwtTokenProvider.createToken(checkedUser.getStudentNumber(), 60L);

        Map<String, String> map = new HashMap<>();
        map.put("accessToken", accessToken);
        map.put("refreshToken", refreshToken);

        return map;

    }

    @Transactional
    public String updatePw(String refreshToken,String nowPwd,String newPwd) {

        String username = jwtTokenProvider.getUserPk(refreshToken);

        // id??????
        User checkedUser = userRepository2.findByStudentNumber(username)
                .orElseThrow(() -> new IllegalArgumentException("id??? ???????????? ????????????."));

        // ?????? ??????
        if (!passwordEncoder.matches(nowPwd, checkedUser.getPassword())) {
            return "pwdError";
        }

        userRepository2.updatePwd(username, passwordEncoder.encode(newPwd));
        return "accepted";

    }

    @Transactional
    public String deleteUser(String refreshToken,String nowPwd) {

        String studentNumber = jwtTokenProvider.getUserPk(refreshToken);

        // id??????
        User checkedUser = userRepository2.findByStudentNumber(studentNumber)
                .orElseThrow(() -> new IllegalArgumentException("id??? ???????????? ????????????."));

        // ?????? ??????
        if (!passwordEncoder.matches(nowPwd, checkedUser.getPassword())) {
            return "pwdError";
        }
        int userId = userRepository2.findUserIdByStudentNumber(studentNumber);

        //db?????? ??????./

        return "accepted";

    }

    @Transactional
    public String searchPw(String id,String name,String email) throws MessagingException {
        // id??????
        User checkedUser = userRepository2.findByStudentNumber(id)
                .orElseThrow(() -> new IllegalArgumentException("noSearched"));

        if(!checkedUser.getName().equals(name) || !checkedUser.getEmail().equals(email)){
            throw new IllegalArgumentException("noSearched");
        }

        String tempPwd = getAuthCode();
        userRepository2.updatePwd(id, passwordEncoder.encode(tempPwd));

        // ?????? ??????

        String content = "<h4>???????????????.</h4><h4>Sejong Coding Helper?????????.</h4>" + "<h4>??????????????? ?????? ??????????????? ?????????????????????. ?????? ??????????????? ??????" +
                "???????????? ???????????? ???, ????????? ??????????????? ??????????????????.</h4>" +
                "<h2>?????? ?????? ?????? : " + "<b><u>" + tempPwd + "</u></b><h2>" + "<h4>???????????????.</h4>";

        // ?????? ?????????
        MimeMessage message = javaMailSender.createMimeMessage();

        message.setFrom("Sejong Coding Helper<sjhelper10@gmail.com>");
        message.setSubject("Sejong Coding Helper ?????? ???????????? ??????.");
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
        message.setText(content, "UTF-8", "html");
        message.setSentDate(new Date());

        javaMailSender.send(message);
        System.out.println(tempPwd);

        return "accepted";
    }

    public Map<String, String> refreshToken(String refreshToken) {
        String accessToken = "";

        String username = jwtTokenProvider.getUserPk(refreshToken);

        if (jwtTokenProvider.validateToken(refreshToken)) {
            accessToken = jwtTokenProvider.createToken(username, 60L);
            refreshToken = jwtTokenProvider.createToken(username, 120L);
        } else {
            throw new IllegalArgumentException("?????? ??????");
        }

        if (refreshToken == null || "".equals(refreshToken)) {
            throw new IllegalArgumentException("?????? ??????");
        }

        Map<String, String> map = new HashMap<>();
        map.put("accessToken", accessToken);
        map.put("refreshToken", refreshToken);

        return map;
    }

    //?????? ???????????? ?????? ??????
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

    // ?????? ?????? ??????
    public List<User> read(){
        return userRepository2.findAll();
    }

    public Optional<User> readOne(Long id){
        return userRepository2.findById(id);
    }

    public void deleteAccount(Long id) {
        userRepository2.deleteById(id);
    }

    public Map<String, Object> findByAssistant(String refreshToken){
        String studentNumber = jwtTokenProvider.getUserPk(refreshToken);
        List<Map<String, Object>> assistantDTO2 = userRepository2.findIsAssistantByStudentNumber(studentNumber);
        Map<String, Object> map = new HashMap<>();
        map.put("name", assistantDTO2.get(0).get("name"));
        map.put("isAssistant", assistantDTO2.get(1).get("name"));
        map.put("studentNumber",studentNumber);
        map.put("email",findUserEmailByStudentNumber(studentNumber));
        map.put("id", findUserIdByStudentNumber(studentNumber));
        return map;
    }

    public int findUserIdByStudentNumber(String studentNumber){
        try{
            return userRepository2.findUserIdByStudentNumber(studentNumber);
        }catch(Exception e){
            return 0;
        }
    }

    public String findUserEmailByStudentNumber(String studentNumber){
        return userRepository2.findUserEmailByStudentNumber(studentNumber);
    }


}
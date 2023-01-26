package com.example.testlocal.module.user.application.service.impl;

import com.example.testlocal.config.RoleType;
import com.example.testlocal.core.exception.InvalidUserIdException;
import com.example.testlocal.core.security.JwtTokenProvider;
import com.example.testlocal.module.user.application.dto.UserDto;
import com.example.testlocal.module.user.application.dto.request.UserInfoRequest;
import com.example.testlocal.module.user.application.dto.response.EmailCheckResponse;
import com.example.testlocal.module.user.application.dto.response.UserInfoResponse;
import com.example.testlocal.module.user.domain.entity.User;
import com.example.testlocal.module.user.domain.repository.UserRepository2;
import lombok.RequiredArgsConstructor;
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

    public User create(UserDto requestDTO) {
        User user = User.builder().email(requestDTO.getEmail()).name(requestDTO.getName()).password(requestDTO.getPassword()).studentNumber(requestDTO.getStudentNumber()).roleType(RoleType.USER).build();
        return userRepository2.save(user);
    }

    public User findById(Long id) {
        return userRepository2.findById(id).orElseThrow(() -> new InvalidUserIdException());
    }

    @Transactional
    public UserInfoResponse signUp(UserInfoRequest userInfoRequest) {
        // pw를 암호화하는 과정
        userInfoRequest.setPwd(passwordEncoder.encode(userInfoRequest.getPwd()));
        User user = User.builder().studentNumber(userInfoRequest.getStudentNumber()).name(userInfoRequest.getName()).email(userInfoRequest.getEmail()).password(userInfoRequest.getPwd()).build();
        return UserInfoResponse.of(userInfoRequest.getStudentNumber(), true);
    }

    public Map<String, String> login(Map<String, String> user) {

        String accessToken = "";
        String refreshToken = "";

        // id확인
        User checkedUser = userRepository2.findByStudentNumber(user.get("id"))
                .orElseThrow(() -> new IllegalArgumentException("id가 존재하지 않습니다."));

        // 비번 확인
        if (!passwordEncoder.matches(user.get("pwd"), checkedUser.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀 번호입니다.");
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

        // id확인
        User checkedUser = userRepository2.findByStudentNumber(username)
                .orElseThrow(() -> new IllegalArgumentException("id가 존재하지 않습니다."));

        // 비번 확인
        if (!passwordEncoder.matches(nowPwd, checkedUser.getPassword())) {
            return "pwdError";
        }

        userRepository2.updatePwd(username, passwordEncoder.encode(newPwd));
        return "accepted";

    }

    @Transactional
    public String deleteUser(String refreshToken,String nowPwd) {

        String studentNumber = jwtTokenProvider.getUserPk(refreshToken);

        // id확인
        User checkedUser = userRepository2.findByStudentNumber(studentNumber)
                .orElseThrow(() -> new IllegalArgumentException("id가 존재하지 않습니다."));

        // 비번 확인
        if (!passwordEncoder.matches(nowPwd, checkedUser.getPassword())) {
            return "pwdError";
        }
        int userId = userRepository2.findUserIdByStudentNumber(studentNumber);

        //db에서 삭제./

        return "accepted";

    }

    @Transactional
    public String searchPw(String id,String name,String email) throws MessagingException {
        // id확인
        User checkedUser = userRepository2.findByStudentNumber(id)
                .orElseThrow(() -> new IllegalArgumentException("noSearched"));

        if(!checkedUser.getName().equals(name) || !checkedUser.getEmail().equals(email)){
            throw new IllegalArgumentException("noSearched");
        }

        String tempPwd = getAuthCode();
        userRepository2.updatePwd(id, passwordEncoder.encode(tempPwd));

        // 키값 생성

        String content = "<h4>안녕하세요.</h4><h4>Sejong Coding Helper입니다.</h4>" + "<h4>회원님에게 임시 비밀번호를 발급해드립니다. 임시 비밀번호를 통해" +
                "로그인을 완료하신 후, 반드시 비밀번호를 변경해주세요.</h4>" +
                "<h2>임시 비밀 번호 : " + "<b><u>" + tempPwd + "</u></b><h2>" + "<h4>감사합니다.</h4>";

        // 메일 보내기
        MimeMessage message = javaMailSender.createMimeMessage();

        message.setFrom("Sejong Coding Helper<sjhelper10@gmail.com>");
        message.setSubject("Sejong Coding Helper 임시 비밀번호 발급.");
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
            throw new IllegalArgumentException("토큰 오류");
        }

        if (refreshToken == null || "".equals(refreshToken)) {
            throw new IllegalArgumentException("토큰 오류");
        }

        Map<String, String> map = new HashMap<>();
        map.put("accessToken", accessToken);
        map.put("refreshToken", refreshToken);

        return map;
    }

    //임시 비밀번호 난수 발생
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

    // 전체 유저 읽기
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
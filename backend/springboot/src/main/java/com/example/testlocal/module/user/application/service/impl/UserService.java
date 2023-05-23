package com.example.testlocal.module.user.application.service.impl;

import com.example.testlocal.module.user.application.service.RefreshTokenService;
import com.example.testlocal.module.user.domain.entity.Role;
import com.example.testlocal.module.user.domain.entity.RoleName;
import com.example.testlocal.module.user.domain.repository.RoleRepository;
import com.example.testlocal.util.RoleType;
import com.example.testlocal.core.exception.ConflictException;
import com.example.testlocal.core.exception.ErrorCode;
import com.example.testlocal.core.exception.InvalidUserIdException;
import com.example.testlocal.core.exception.UnauthorizedException;
import com.example.testlocal.core.security.jwt.JwtTokenProvider;
import com.example.testlocal.core.security.jwt.dto.JwtTokenDto;
import com.example.testlocal.module.user.application.dto.UserDto;
import com.example.testlocal.module.user.application.dto.request.LoginRequest;
import com.example.testlocal.module.user.application.dto.request.UserInfoRequest;
import com.example.testlocal.module.user.application.dto.response.UserInfoResponse;
import com.example.testlocal.module.user.application.service.UserCheckService;
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
    private final UserCheckService userCheckServiceImpl;
    private final RoleRepository roleRepository;

    public User create(UserDto requestDTO) {
        ArrayList<Role> roles = new ArrayList<>();
        Role role = new Role();
        roles.add(role);
        User user = User.builder().email(requestDTO.getEmail()).name(requestDTO.getName()).password(requestDTO.getPassword()).studentNumber(requestDTO.getStudentNumber()).roles(roles).build();
        return userRepository2.save(user);
    }

    public User findById(Long id) {
        return userRepository2.findById(id).orElseThrow(() -> new InvalidUserIdException());
    }

    @Transactional
    public UserInfoResponse signUp(UserInfoRequest userInfoRequest) {

        // 비밀번호 일치 확인
        if (!userInfoRequest.getVerifedPwd().equals(userInfoRequest.getPwd())){
            throw new UnauthorizedException(String.format("비밀번호와 비밀번호 확인 값이 다릅니다."), ErrorCode.UNAUTHORIZED_EXCEPTION);
        }

        // pw를 암호화하는 과정
        userInfoRequest.setPwd(passwordEncoder.encode(userInfoRequest.getPwd()));

        // 이메일 중복 확인
        Boolean isOverlapEmail = userCheckServiceImpl.isOverlapEmail(userInfoRequest.getEmail());

        if (!isOverlapEmail){
            throw new ConflictException(String.format("이미 존재하는 이메일 (%s) 입니다", userInfoRequest.getEmail()),
                    ErrorCode.CONFLICT_EMAIL_EXCEPTION);
        }

        // 학번 중복 확인
        Boolean isOverlapStudentNumber = userCheckServiceImpl.isOverlapStudentNumber(userInfoRequest.getStudentNumber());

        if (!isOverlapStudentNumber){
            throw new ConflictException(String.format("이미 존재하는 학번 (%s) 입니다", userInfoRequest.getStudentNumber()),
                    ErrorCode.CONFLICT_STUDENT_NUMBER_EXCEPTION);
        }
        List<Role> list = new ArrayList<>();
        Role role = roleRepository.findByRole(RoleName.ROLE_USER);
        list.add(role);
        User user = userInfoRequest.dtoToEntity(list);
        userRepository2.save(user);

        return UserInfoResponse.of(userInfoRequest.getStudentNumber(), true, "회원가입 성공하였습니다.");
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
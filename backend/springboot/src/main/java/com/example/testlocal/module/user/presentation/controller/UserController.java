package com.example.testlocal.module.user.presentation.controller;

import com.example.testlocal.core.dto.SuccessResponse;
import com.example.testlocal.module.user.application.dto.request.UserAvailabilityRequest;
import com.example.testlocal.module.user.application.dto.response.UserAvailabilityResponse;
import com.example.testlocal.util.Constants;
import com.example.testlocal.module.user.application.dto.UserDto;
import com.example.testlocal.module.user.application.service.impl.UserService;
import com.example.testlocal.module.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Slf4j
@RestController
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@CrossOrigin(origins = Constants.URL , allowCredentials = "true")
public class UserController {

    private final UserService userService;
    private final LoginController loginController;

    @GetMapping("/user")
    public UserDto getUser(@CookieValue(name = "studentNumber") Cookie cookie){
        return userService.findUserByStudentNumber(cookie.getValue());
    }

    @PostMapping("/user/assistant")
    public Map<String, Object> findIsAssistantByStudentNumber(@CookieValue(name = "refreshToken", defaultValue = "-1") String refreshToken) {

        Map<String, Object> map = userService.findByAssistant(refreshToken);
        return map;
    }
    @PostMapping("/user/userID/{studentNumber}")
    public int findUserIdByStudentNumber(@PathVariable String studentNumber) {
        return userService.findUserIdByStudentNumber(studentNumber);
    }

    @PostMapping("/update/pw")
    public String checkPw(@CookieValue(name = "refreshToken", defaultValue = "-1") String refreshToken,
                          @RequestBody Map<String, String> map) {

        String result = userService.updatePw(refreshToken,map.get("nowPwd"),map.get("newPwd"));
        return result;
    }

    @PostMapping("/delete/user")
    public String deleteUser(@CookieValue(name = "refreshToken", defaultValue = "-1") String refreshToken,
                          @RequestBody Map<String, String> map,HttpServletResponse response,HttpServletRequest request) {

        return userService.deleteUser(refreshToken,map.get("nowPwd"));
    }

    @PostMapping("/search/pw")
    public String searchPw(@RequestBody Map<String, String> map) throws MessagingException {
        String result = userService.searchPw(map.get("studentNumber"),map.get("name"),map.get("email")  + "@sju.ac.kr");
        return result;
    }

}
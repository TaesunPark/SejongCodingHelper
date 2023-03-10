package com.example.testlocal.module.user.presentation.controller;

import com.example.testlocal.config.Constants;
import com.example.testlocal.module.user.application.dto.UserDto;
import com.example.testlocal.module.user.application.service.impl.UserService;
import com.example.testlocal.module.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

    @GetMapping("/user")
    public List<User> all() {
        return userService.read();
    }

    @ResponseBody
    @PostMapping("/user")
    public User createUser(@RequestBody UserDto requestDTO) {
        return userService.create(requestDTO);
    }

    @GetMapping("/user/{id}")
    public Optional<User> getUser(@PathVariable Long id) {
        return userService.readOne(id);
    }

    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteAccount(id);
        return "delete User" + id.toString();
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

    @PostMapping("/logincheck")
    public String loginUser(@RequestBody Map<String, String> map, HttpServletResponse response) {

        String accessToken = "";
        String refreshToken = "";

        Map<String, String> resultMap = userService.login(map);
        accessToken = resultMap.get("accessToken");
        refreshToken = resultMap.get("refreshToken");

        Cookie refreshCookie = new Cookie("refreshToken", refreshToken);

        refreshCookie.setMaxAge(120 * 60);   //30??? : 30 * 60
        refreshCookie.setPath("/");
        refreshCookie.setSecure(false);
        refreshCookie.setHttpOnly(true);
        response.addCookie(refreshCookie);

        // ?????????
//        userService.signUp(new UserDto(map.get("studentId"),map.get("id"),map.get("pwd"),map.get("name")));
//        return "good";

        return accessToken;
    }

    @PostMapping("/refreshLoginToken")
    public String refreshLoginToken( @CookieValue(name = "refreshToken", defaultValue = "-1") String refreshToken,
                                    HttpServletResponse response) {

        String accessToken = "";

        if(refreshToken.equals("-1"))
            throw new IllegalArgumentException("?????? ??????");

        Map<String, String> resultMap = userService.refreshToken(refreshToken);
        accessToken = resultMap.get("accessToken");
        refreshToken = resultMap.get("refreshToken");

        Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
        refreshCookie.setMaxAge(120 * 60);   //120??? : 120 * 60
        refreshCookie.setPath("/");
        refreshCookie.setSecure(false);
        refreshCookie.setHttpOnly(true);
        response.addCookie(refreshCookie);

        return accessToken;
    }

    @PostMapping("/userlogout")
    public String logout(HttpServletResponse response,HttpServletRequest request) {

        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setMaxAge(0); // ????????? expiration ????????? 0?????? ?????? ?????????.
        cookie.setPath("/"); // ?????? ???????????? ?????? ????????? ?????????.

        response.addCookie(cookie);

        // ?????? ??????
        HttpSession session = request.getSession();
        session.invalidate(); // ?????? ??????

        return "logout";
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

        String result = userService.deleteUser(refreshToken,map.get("nowPwd"));
        logout(response,request);

        return result;
    }

    @PostMapping("/search/pw")
    public String searchPw(@RequestBody Map<String, String> map) throws MessagingException {
        String result = userService.searchPw(map.get("studentNumber"),map.get("name"),map.get("email")  + "@sju.ac.kr");
        return result;
    }

}
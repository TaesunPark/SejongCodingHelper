package com.example.testlocal.module.user.presentation.controller;

import com.example.testlocal.config.Constants;
import com.example.testlocal.module.user.application.dto.UserDto;
import com.example.testlocal.module.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@CrossOrigin(origins = Constants.URL , allowCredentials = "true")
public class UserController {

    @ResponseBody
    @PostMapping("/user/signup")
    public User signUp(@RequestBody UserDto requestDTO) {
        return null;
    }


}

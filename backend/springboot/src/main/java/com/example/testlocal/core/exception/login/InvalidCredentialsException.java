package com.example.testlocal.core.exception.login;

import com.example.testlocal.core.exception.ErrorCode;
import com.example.testlocal.core.exception.UnauthorizedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class InvalidCredentialsException extends UnauthorizedException {
    public InvalidCredentialsException() {
        super("아이디(학번) 또는 비밀번호를 잘못 입력했습니다.");
    }

}

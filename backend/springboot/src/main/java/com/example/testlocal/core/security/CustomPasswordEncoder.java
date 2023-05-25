package com.example.testlocal.core.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        // 비밀번호 인코딩 로직을 구현
        // 예: BCryptPasswordEncoder를 사용하여 비밀번호 인코딩
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        // 비밀번호 일치 여부 확인 로직을 구현
        // 예: BCryptPasswordEncoder를 사용하여 비밀번호 일치 여부 확인
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(rawPassword, encodedPassword);
    }
}

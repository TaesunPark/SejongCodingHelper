package com.example.testlocal.core.security.jwt.service;


import com.example.testlocal.core.security.jwt.JwtTokenProvider;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenService {
    private JwtTokenProvider jwtTokenProvider;


    public JwtTokenService(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String refreshTokenToStudentNumber(String refreshToken){
        return jwtTokenProvider.getUserPk(refreshToken);
    }

}

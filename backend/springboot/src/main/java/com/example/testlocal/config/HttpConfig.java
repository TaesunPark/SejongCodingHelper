package com.example.testlocal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.Cookie;

@Configuration
public class HttpConfig {

    @Bean(name = "refreshTokenCookie")
    public Cookie cookie(){
        Cookie cookie = new Cookie("refreshToken", "");
        cookie.setMaxAge(120 * 60);   //120분 : 120 * 60
        cookie.setPath("/");
        cookie.setSecure(false);
        cookie.setHttpOnly(true);
        return cookie;
    }
}

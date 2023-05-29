package com.example.testlocal.core.security.jwt.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String refreshToken;
    private String username;
    private String email;
    private List<GrantedAuthority> roles;

    public JwtResponse(String accessToken, String refreshToken, String username, String email, List<GrantedAuthority> roles) {
        this.token = accessToken;
        this.refreshToken = refreshToken;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}

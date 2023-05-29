package com.example.testlocal.core.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private CustomUserDetailService userDetailsService;
    private PasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider(CustomUserDetailService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        CustomUserDetails user = (CustomUserDetails) userDetailsService.loadUserByUsername(username);

        // 사용자가 없을 때
        if (user == null) {
            throw new BadCredentialsException("username is not found. username=" + username);
        }
        // 아이디는 있는데 비밀번호가 다를때
        // lock 작업, 5회 이상 틀리면 lock
        if (!this.passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("password is not matched");
        }

        return new CustomAuthenticationToken(username, user.getEmail(), user.getAuthorities());
    }

    @Override
    public boolean supports(Class<? extends Object> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

    public void setUserDetailsService(CustomUserDetailService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}

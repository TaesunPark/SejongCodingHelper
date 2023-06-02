package com.example.testlocal.core.security;

import com.example.testlocal.core.exception.login.InvalidCredentialsException;
import com.example.testlocal.module.user.domain.repository.UserRepository2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private CustomUserDetailService userDetailsService;

    @Autowired
    private UserRepository2 userRepository;
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

        // 초과 횟수가 5회가 넘으면
        if (user.getPasswordCount() >= 5){
            throw new InvalidCredentialsException("비밀번호 5회 초과로"+username+"(계정)이 잠겼습니다.");
        }

        // 아이디는 있는데 비밀번호가 다를때
        // lock 작업, 5회 이상 틀리면 lock
        if (!this.passwordEncoder.matches(password, user.getPassword())) {
            userRepository.save(user.addPasswordCount());
            throw new InvalidCredentialsException();
        }

        if (user.getPasswordCount() != 0) {
            userRepository.save(user.initPasswordCount());
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

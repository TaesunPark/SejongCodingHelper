package com.example.testlocal.module.user.application.service.impl;

import com.example.testlocal.core.security.CustomUserDetails;
import com.example.testlocal.module.user.domain.repository.UserRepository2;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository2 userRepository;
    @Override
    public UserDetails loadUserByUsername(String studentNumber) throws UsernameNotFoundException {
        return new CustomUserDetails(userRepository.findByStudentNumber(studentNumber)
                .orElseThrow(
                        () -> new UsernameNotFoundException("사용자를 찾을 수 없습니다.")
                )
        );
    }
}

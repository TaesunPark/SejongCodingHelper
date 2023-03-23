package com.example.testlocal.module.user.application.service;

import com.example.testlocal.core.exception.NotFoundException;
import com.example.testlocal.module.user.domain.entity.RefreshToken;
import com.example.testlocal.module.user.domain.entity.User;
import com.example.testlocal.module.user.domain.repository.UserRepository2;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository2 userRepository;

    public void updateRefreshToken(Long userId, String refreshToken) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("유저가 존재하지 않습니다."));
        RefreshToken refreshToken =
        userRepository.save(user);
    }
}

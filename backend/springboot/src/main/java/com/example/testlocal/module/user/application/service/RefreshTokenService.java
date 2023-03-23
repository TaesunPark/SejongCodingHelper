package com.example.testlocal.module.user.application.service;

import com.example.testlocal.core.exception.*;
import com.example.testlocal.module.user.domain.entity.RefreshToken;
import com.example.testlocal.module.user.domain.entity.User;
import com.example.testlocal.module.user.domain.repository.RefreshTokenRepository;
import com.example.testlocal.module.user.domain.repository.UserRepository2;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.DoubleStream;

import static com.example.testlocal.core.exception.ErrorCode.VALIDATION_EXCEPTION;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository2 userRepository;

    @Value("${sjc.app.jwtExpiration.refreshToken}")
    private int refreshTokenDurationMs;

    public RefreshToken createRefreshToken(String username) {

        User user = userRepository.findByStudentNumber(username).orElseThrow(() -> new NotFoundException(String.format("존재하지 않는 학번 (%s) 입니다", username),
                ErrorCode.NOT_FOUND_USER_EXCEPTION));

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new ForbiddenException(String.format("이미 기간 지난 토큰 (%s) 입니다", token.getToken()),
                    ErrorCode.FORBIDDEN_TOKEN_REFRESH_EXCEPTION);
        }

        return token;
    }
    @Transactional
    public int deleteByUsername(String username) {
        return refreshTokenRepository.deleteByUser(userRepository.findByStudentNumber(username).get());
    }

    public Optional<RefreshToken> findByToken(String requestRefreshToken) {
        return Optional.ofNullable(refreshTokenRepository.findByToken(requestRefreshToken).orElseThrow(() -> new NotFoundException(requestRefreshToken,
                ErrorCode.NOT_FOUND_REFRESH_TOKEN_EXCEPTION)));
    }
}

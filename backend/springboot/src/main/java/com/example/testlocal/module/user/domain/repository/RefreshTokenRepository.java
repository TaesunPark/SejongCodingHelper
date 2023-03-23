package com.example.testlocal.module.user.domain.repository;

import com.example.testlocal.module.user.domain.entity.RefreshToken;
import com.example.testlocal.module.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    Integer deleteByUser(User user);
}
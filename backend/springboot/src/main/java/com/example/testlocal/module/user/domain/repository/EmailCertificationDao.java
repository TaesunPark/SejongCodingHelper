package com.example.testlocal.module.user.domain.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@RequiredArgsConstructor
@Repository
public class EmailCertificationDao {
    private final String REDIS_EMAIl_PREFIX = "email:"; // Redis에 저장되는 Key값이 중복되지 않도록 상수 선언
    private final int LIMIT_TIME = 10 * 60; // Redis에서 해당 데이터의 유효시간(TTL)을 설정
    private final StringRedisTemplate stringRedisTemplate;

    public void createCodeCertification(String email, String certificationNumber){
        stringRedisTemplate.opsForValue()
                .set(REDIS_EMAIl_PREFIX + email, certificationNumber, Duration.ofSeconds(LIMIT_TIME));
    }

    // 이메일에 해당하는 인증번호 가져오기
    public String getCodeCertification(String email){
        return stringRedisTemplate.opsForValue().get(REDIS_EMAIl_PREFIX + email);
    }

    // 인증이 완료되었을 경우 메모리 관리를 위해 인증번호 삭제
    public void removeCodeCertification(String email){
        stringRedisTemplate.delete(REDIS_EMAIl_PREFIX + email);
    }

    // Redis에 이메일(KEY)로 저장된 인증번호(VALUE)가 존재하는지 확인
    public boolean hasKey(String email){
        return stringRedisTemplate.hasKey(REDIS_EMAIl_PREFIX + email);
    }
}

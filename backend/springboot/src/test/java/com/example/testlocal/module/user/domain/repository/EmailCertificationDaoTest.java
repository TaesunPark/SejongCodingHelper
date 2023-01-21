package com.example.testlocal.module.user.domain.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@WebAppConfiguration
@SpringBootTest
class EmailCertificationDaoTest {
    @Autowired
    private EmailCertificationDao emailCertificationDao;

    private final String email = "tovbsvb@sju.ac.kr";

    @AfterEach
    public void tearDown() throws Exception {
        emailCertificationDao.removeCodeCertification(email);
    }

    @DisplayName("기본_등록_조회기능")
    @Test
    public void createCodeCertification() {
        //given
        emailCertificationDao.createCodeCertification(email, "12345");
        assertThat(emailCertificationDao.getCodeCertification(email)).isEqualTo("12345");
    }
}
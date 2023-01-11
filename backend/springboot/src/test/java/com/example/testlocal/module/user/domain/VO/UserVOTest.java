package com.example.testlocal.module.user.domain.VO;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserVOTest {

    @DisplayName("VO 동등비교를 한다.")
    @Test
    void isSameObjects() {
        UserVO user1 = UserVO.builder().name("박태순").email("tovbs111@daum.net").password("1234").studentNumber("17011526").build();
        UserVO user2 = UserVO.builder().name("박태순").email("tovbs111@daum.net").password("1234").studentNumber("17011526").build();

        assertThat(user1).isEqualTo(user2);
        assertThat(user1).hasSameHashCodeAs(user2);
    }

    @DisplayName("VO 같지 않다. 테스트")
    @Test
    void isNotSameObjects() {
        UserVO user1 = UserVO.builder().name("박태리아").email("tovbs111@daum.net").password("1234").studentNumber("17011527").build();
        UserVO user2 = UserVO.builder().name("박태순").email("tovbs111@daum.net").password("1234").studentNumber("17011527").build();

        assertThat(user1).isNotSameAs(user2);
    }
}
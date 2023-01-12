package com.example.testlocal.module.user.domain.entity;

import com.example.testlocal.config.RoleType;
import com.example.testlocal.module.user.domain.repository.UserRepository2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserTest {
    @Autowired
    private UserRepository2 userRepository;

    private User user1;
    private User user2;


    @BeforeEach
    public void init(){
        user1 = User.builder().email("tovbskvb@daum.net").studentNumber("17011526").password("1234").name("손민기").roleType(RoleType.ADMIN).build();
        user2 = userRepository.save(user1);
    }

    @DisplayName("유저 생성, 테스트")
    @Test
    public void create(){
        assertThat(user1.getName()).isEqualTo(user2.getName());
        assertThat(user1.getId()).isEqualTo(user2.getId());
        assertThat(user1.getPassword()).isEqualTo(user2.getPassword());
        assertThat(user1.getRoleType()).isEqualTo(user2.getRoleType());
        assertThat(user1.getStudentNumber()).isEqualTo(user2.getStudentNumber());
        assertThat(user1.getEmail()).isEqualTo("tovbskvb@daum.net");
        assertThat(user1.getRoleType()).isEqualTo(RoleType.ADMIN);
        assertThat(user2.getCreateAt()).isEqualTo(user2.getUpdateAt());
    }

    @DisplayName("유저 업데이트, 테스트")
    @Test
    public void update(){
        user1.setEmail("tovbskvb1@daum.net");
        user1.setName("발민기");
        user1.setStudentNumber("17011568");
        user1.setRoleType(RoleType.USER);
        user2 = userRepository.save(user1);
        assertThat(user1.getName()).isEqualTo(user2.getName());
        assertThat(user1.getId()).isEqualTo(user2.getId());
        assertThat(user1.getPassword()).isEqualTo(user2.getPassword());
        assertThat(user1.getRoleType()).isEqualTo(user2.getRoleType());
        assertThat(user1.getStudentNumber()).isEqualTo(user2.getStudentNumber());
        assertThat(user1.getEmail()).isEqualTo("tovbskvb1@daum.net");
        assertThat(user1.getRoleType()).isEqualTo(RoleType.USER);
    }

    @DisplayName("유저 삭제")
    @Test
    public void delete(){
        userRepository.delete(user1);
        assertThat(userRepository.findByEmail("tovbskvb@daum.net")).isEmpty();
    }

}
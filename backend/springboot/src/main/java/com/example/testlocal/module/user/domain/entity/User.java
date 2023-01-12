package com.example.testlocal.module.user.domain.entity;

import com.example.testlocal.config.DateTime;
import com.example.testlocal.config.RoleType;
import com.example.testlocal.domain.dto.UserDTO2;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "user")
@Table(name = "user")
public class User extends DateTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Setter
    @Column(name = "student_number", nullable = false)
    private String studentNumber;

    @Column(name = "password", nullable = false)
    private String password;

    @Setter
    @Column(name = "name", nullable = false)
    private String name;

    @Setter
    @Column(name = "email", nullable = false)
    private String email;

    public User(UserDTO2 userDTO) {
        this.studentNumber = userDTO.getStudentNumber();
        this.password = userDTO.getPassword();
        this.name = userDTO.getName();
        this.email = userDTO.getEmail();
    }
    
    @Setter
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

}

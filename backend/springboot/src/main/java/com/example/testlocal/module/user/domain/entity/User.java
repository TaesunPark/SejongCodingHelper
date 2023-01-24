package com.example.testlocal.module.user.domain.entity;

import com.example.testlocal.config.DateTime;
import com.example.testlocal.config.RoleType;
import lombok.*;

import javax.persistence.*;

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

    @Column(name = "student_number", nullable = false, length = 8, unique = true)
    private String studentNumber;

    @Column(name = "password", nullable = false, length = 200, unique = true)
    private String password;

    @Column(name = "name", nullable = false, length = 40)
    private String name;

    @Column(name = "email", nullable = false, length = 60, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

}

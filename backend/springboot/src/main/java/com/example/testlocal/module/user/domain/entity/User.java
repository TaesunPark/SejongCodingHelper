package com.example.testlocal.module.user.domain.entity;

import com.example.testlocal.util.DateTime;
import com.example.testlocal.util.RoleType;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

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

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "USER_AUTHORITY", joinColumns = {
            @JoinColumn(name = "id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "role_id", referencedColumnName = "role_id")})
    private Set<Role> roles = new HashSet<>();

    @Builder(builderMethodName = "createUser")
    public User(
            @NotNull @Size(max = 512) String email,
            @Size(max = 10) String studentNumber,
            @Size(max = 128) String password,
            @Size(max = 100) String name,
            @Size(max = 10) Set role
    ) {
        this.email = email;
        this.name = name;
        this.studentNumber = studentNumber;
        this.roles = role;
        this.password = password;
    }
}

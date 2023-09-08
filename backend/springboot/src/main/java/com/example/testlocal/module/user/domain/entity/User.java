package com.example.testlocal.module.user.domain.entity;

import com.example.testlocal.module.chat.domain.RoomUser;
import com.example.testlocal.util.DateTime;
import com.example.testlocal.util.RoleType;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
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

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(name = "user_with_role",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;

    @Column(name = "password_count", nullable = false)
    private int passwordCount;

    @OneToMany(fetch = FetchType.LAZY)
    @Getter
    @Setter
    private List<RoomUser> roomUserList;


    public Integer addPasswordCount(){
        passwordCount += 1;
        return passwordCount;
    }

    public void setPasswordCount(){
        passwordCount = 0;
    }

    @Override
    public String toString(){
        return "id=" + id + ", 학번="+studentNumber+", 이름="+name;
    }

}

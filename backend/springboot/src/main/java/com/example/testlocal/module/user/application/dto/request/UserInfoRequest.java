package com.example.testlocal.module.user.application.dto.signup.request;

import com.example.testlocal.config.RoleType;
import com.example.testlocal.module.user.domain.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserInfoRequest {
    private String studentNumber;

    private String pwd;

    // 비밀번호 확인
    private String verifedPwd;

    private String name;

    private String email;

    private RoleType roleType;

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerifedPwd() {
        return verifedPwd;
    }

    public void setVerifedPwd(String verifedPwd) {
        this.verifedPwd = verifedPwd;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public User dtoToEntity(){
        return User.createUser()
                .email(email)
                .roleType(roleType)
                .studentNumber(studentNumber)
                .name(name)
                .password(pwd).build();
    }

}

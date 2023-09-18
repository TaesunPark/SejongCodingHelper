package com.example.testlocal.module.user.application.dto.request;

import com.example.testlocal.module.user.domain.entity.Role;
import com.example.testlocal.util.RoleType;
import com.example.testlocal.module.user.domain.entity.User;

import java.util.List;
import java.util.Set;

public class UserInfoRequest {
    private String studentNumber;
    private String pwd;

    // 비밀번호 확인
    private String verifedPwd;

    private String name;

    private String email;

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
    public User dtoToEntity(List<Role> roleList){
        return User.builder()
                .passwordCount(0)
                .email(email)
                .roles(roleList)
                .studentNumber(studentNumber)
                .name(name)
                .password(pwd).build();
    }

}

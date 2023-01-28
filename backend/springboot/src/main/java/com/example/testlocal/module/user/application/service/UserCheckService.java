package com.example.testlocal.module.user.application.service;

import com.example.testlocal.module.user.application.dto.response.EmailCheckResponse;

public interface UserCheckService {

    /* 중복되면 false, 중복 안되면 true */
    public Boolean isOverlapEmail(String email);
    public Boolean isOverlapStudentNumber(String studentNumber);
}

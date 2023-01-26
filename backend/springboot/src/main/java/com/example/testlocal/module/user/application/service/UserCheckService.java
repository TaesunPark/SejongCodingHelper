package com.example.testlocal.module.user.application.service;

import com.example.testlocal.module.user.application.dto.response.EmailCheckResponse;

public interface UserCheckService {

    public Boolean isOverlapEmail(String email);
    public Boolean isOverlapStudentNumber(String studentNumber);
}

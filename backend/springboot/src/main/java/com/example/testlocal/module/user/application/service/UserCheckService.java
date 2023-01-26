package com.example.testlocal.module.user.application.service;

import com.example.testlocal.module.user.application.dto.response.EmailCheckResponse;

public interface UserCheckService {

    public EmailCheckResponse isOverlapEmail(String email);
}

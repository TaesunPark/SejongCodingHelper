package com.example.testlocal.module.user.application.service.impl;

import com.example.testlocal.module.user.application.dto.response.EmailCheckResponse;
import com.example.testlocal.module.user.application.service.UserCheckService;
import com.example.testlocal.module.user.domain.entity.User;
import com.example.testlocal.module.user.domain.repository.UserRepository2;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserCheckServiceImpl implements UserCheckService {

    private final UserRepository2 userRepository;
    @Override
    public EmailCheckResponse isOverlapEmail(String email) {

        Boolean isPresented = userRepository.existsUserByEmail(email);

        if (!isPresented) {
            return EmailCheckResponse.of(email, true);
        }

        return EmailCheckResponse.of(email, false);
    }
}

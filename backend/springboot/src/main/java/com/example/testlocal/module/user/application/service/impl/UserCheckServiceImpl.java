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
    public Boolean isOverlapEmail(String email) {
        Boolean isPresented = userRepository.existsUserByEmail(email);

        if (isPresented){
            return false;
        }
        return true;
    }

    @Override
    public Boolean isOverlapStudentNumber(String studentNumber) {
        Boolean isPresented = userRepository.existsUserByStudentNumber(studentNumber);
        return isPresented;
    }
}

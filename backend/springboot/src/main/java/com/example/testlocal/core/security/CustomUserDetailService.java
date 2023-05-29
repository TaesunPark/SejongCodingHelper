package com.example.testlocal.core.security;

import com.example.testlocal.core.exception.login.InvalidCredentialsException;
import com.example.testlocal.module.user.domain.entity.User;
import com.example.testlocal.module.user.domain.repository.UserRepository2;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository2 userRepository;
    @Override
    public UserDetails loadUserByUsername(String studentNumber) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByStudentNumber(studentNumber);

        if (user.isEmpty()){
            throw new UsernameNotFoundException("잘못된 접근");
        }

        return new CustomUserDetails(user.get());
    }
}
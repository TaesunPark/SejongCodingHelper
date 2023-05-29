package com.example.testlocal.module.user.presentation.controller;

import com.example.testlocal.core.dto.SuccessCode;
import com.example.testlocal.core.dto.SuccessResponse;
import com.example.testlocal.core.security.CustomUserDetails;
import com.example.testlocal.core.security.jwt.JwtUtils;
import com.example.testlocal.core.security.jwt.dto.JwtResponse;
import com.example.testlocal.core.security.jwt.dto.JwtTokenDto;
import com.example.testlocal.core.security.jwt.dto.TokenRefreshRequest;
import com.example.testlocal.module.user.application.dto.request.LoginRequest;
import com.example.testlocal.module.user.application.service.RefreshTokenService;
import com.example.testlocal.module.user.domain.entity.RefreshToken;
import com.example.testlocal.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = Constants.URL , allowCredentials = "true")
public class LoginController {

    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<SuccessResponse<JwtResponse>> loginUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication;
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getId(), loginRequest.getPwd());

        authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken((String) authentication.getPrincipal());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken((String) authentication.getPrincipal());

        return SuccessResponse.success(SuccessCode.LOGIN_SUCCESS, new JwtResponse(jwt, refreshToken.getToken(), (String) authentication.getPrincipal(),
                (String) authentication.getCredentials(), (List<GrantedAuthority>) authentication.getAuthorities()));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<SuccessResponse<JwtTokenDto>> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
        return refreshTokenService.findByToken(request.getRefreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = refreshTokenService.createRefreshToken(user.getStudentNumber()).getToken();
                    return SuccessResponse.success(SuccessCode.TOKEN_SUCCESS, JwtTokenDto.builder().accessToken(accessToken).refreshToken(request.getRefreshToken()).build());
                }).get();
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        refreshTokenService.deleteByUsername(username);
        return SuccessResponse.success(SuccessCode.OK_SUCCESS,"로그아웃 성공");
    }


}

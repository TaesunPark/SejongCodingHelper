package com.example.testlocal.module.user.presentation.controller;

import com.example.testlocal.core.dto.SuccessCode;
import com.example.testlocal.core.dto.SuccessResponse;
import com.example.testlocal.core.security.jwt.CustomUserDetails;
import com.example.testlocal.core.security.jwt.JwtUtils;
import com.example.testlocal.core.security.jwt.dto.JwtResponse;
import com.example.testlocal.core.security.jwt.dto.JwtTokenDto;
import com.example.testlocal.core.security.jwt.dto.TokenRefreshRequest;
import com.example.testlocal.module.user.application.dto.request.LoginRequest;
import com.example.testlocal.module.user.application.service.RefreshTokenService;
import com.example.testlocal.module.user.domain.entity.RefreshToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
//@CrossOrigin(origins = Constants.URL , allowCredentials = "true")
public class LoginController {
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping("/logincheck")
    public ResponseEntity<SuccessResponse<JwtResponse>> loginUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getId(), loginRequest.getPwd()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername());

        return SuccessResponse.success(SuccessCode.LOGIN_SUCCESS, new JwtResponse(jwt, refreshToken.getToken(), userDetails.getUsername(),
                userDetails.getEmail(), roles));
    }

    @PostMapping("/refreshLoginToken")
    public ResponseEntity<SuccessResponse<JwtTokenDto>> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        RefreshToken refreshToken = refreshTokenService.findByToken(requestRefreshToken).get();

        String token = jwtUtils.generateTokenFromUsername(refreshTokenService.verifyExpiration(refreshToken).getUser().getStudentNumber());

        return SuccessResponse.success(SuccessCode.REFRESH_TOKEN_SUCCESS, new JwtTokenDto(token, requestRefreshToken));

    }

    @PostMapping("/userlogout")
    public ResponseEntity<?> logoutUser() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        refreshTokenService.deleteByUsername(username);
        return SuccessResponse.success(SuccessCode.OK_SUCCESS,"로그아웃 성공");
    }


}

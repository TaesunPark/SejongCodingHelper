package com.example.testlocal.module.user.presentation.controller;

import com.example.testlocal.core.dto.SuccessCode;
import com.example.testlocal.core.dto.SuccessResponse;
import com.example.testlocal.core.security.jwt.JwtUtils;
import com.example.testlocal.core.security.jwt.dto.JwtResponse;
import com.example.testlocal.module.user.application.dto.request.LoginRequest;
import com.example.testlocal.module.user.application.service.RefreshTokenService;
import com.example.testlocal.module.user.domain.entity.RefreshToken;
import com.example.testlocal.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = Constants.URL , allowCredentials = "true")
@Controller
public class VeiwController {

    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    // 뷰 던짐x
    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
    }

    @PostMapping("/login1")
    public ResponseEntity<SuccessResponse<JwtResponse>> loginUser(@ModelAttribute("loginRequest") LoginRequest loginRequest) {
        Authentication authentication;
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getId(), loginRequest.getPwd());

        authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken((String) authentication.getPrincipal());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken((String) authentication.getPrincipal());

        return SuccessResponse.success(SuccessCode.LOGIN_SUCCESS, new JwtResponse(jwt, refreshToken.getToken(), (String) authentication.getPrincipal(),
                (String) authentication.getCredentials(), (List<GrantedAuthority>) authentication.getAuthorities()));
    }
}

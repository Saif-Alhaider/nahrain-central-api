package io.github.saifalhaider.nahrain.nahrain_central_api.auth.controller;

import io.github.saifalhaider.nahrain.nahrain_central_api.auth.model.dto.AuthenticationResponseDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.model.dto.LoginRequestDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.model.dto.RegisterRequestDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.LoginService;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.RegisterService;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.jwt.RefreshTokenService;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.ApiResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@CrossOrigin
@RequiredArgsConstructor
public class AuthenticationController {
    private final RegisterService registerService;
    private final LoginService loginService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponseDto<AuthenticationResponseDto>> register(
            @RequestBody RegisterRequestDto request
    ) {
        return registerService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto<AuthenticationResponseDto>> login(
            @RequestBody LoginRequestDto request
    ) {
        return loginService.login(request);
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<ApiResponseDto<AuthenticationResponseDto>> refreshToken(HttpServletRequest request) {
        return refreshTokenService.refreshToken(request);
    }

}

package io.github.saifalhaider.nahrain.nahrain_central_api.auth.controller;

import io.github.saifalhaider.nahrain.nahrain_central_api.auth.model.dto.AuthenticationResponseDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.model.dto.LoginRequestDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.model.dto.RefreshTokenDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.model.dto.RegisterRequestDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.LoginService;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.RefreshTokenService;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.RegisterService;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
  private final RegisterService registerService;
  private final LoginService loginService;
  private final RefreshTokenService refreshTokenService;

  @PostMapping("/register")
  public ResponseEntity<ApiResponseDto<AuthenticationResponseDto>> register(
      @RequestBody RegisterRequestDto request) {
    return registerService.register(request);
  }

  @PostMapping("/login")
  public ResponseEntity<ApiResponseDto<AuthenticationResponseDto>> login(
      @RequestBody LoginRequestDto request) {
    return loginService.login(request);
  }

  @PostMapping("/refreshtoken")
  public ResponseEntity<ApiResponseDto<AuthenticationResponseDto>> refreshToken(
      @RequestBody RefreshTokenDto request) {
    return refreshTokenService.refreshToken(request);
  }
}

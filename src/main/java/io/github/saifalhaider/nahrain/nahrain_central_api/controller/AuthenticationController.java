package io.github.saifalhaider.nahrain.nahrain_central_api.controller;

import io.github.saifalhaider.nahrain.nahrain_central_api.dto.authDto.LoginRequestDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.dto.authDto.AuthenticationResponseDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.dto.authDto.RegisterRequestDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.service.auth.LoginService;
import io.github.saifalhaider.nahrain.nahrain_central_api.service.auth.RegisterService;
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

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> register(
            @RequestBody RegisterRequestDto request
    ) {
        return ResponseEntity.ok(registerService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> register(
            @RequestBody LoginRequestDto request
    ){
        return ResponseEntity.ok(loginService.login(request));
    }
}

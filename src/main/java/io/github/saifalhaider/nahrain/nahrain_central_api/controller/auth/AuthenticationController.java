package io.github.saifalhaider.nahrain.nahrain_central_api.controller.auth;

import io.github.saifalhaider.nahrain.nahrain_central_api.model.dto.ApiResponseDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.model.dto.auth.AuthenticationResponseDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.model.dto.auth.LoginRequestDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.model.dto.auth.RegisterRequestDto;
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
}

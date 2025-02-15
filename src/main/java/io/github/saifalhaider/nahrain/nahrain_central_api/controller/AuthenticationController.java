package io.github.saifalhaider.nahrain.nahrain_central_api.controller;

import io.github.saifalhaider.nahrain.nahrain_central_api.dto.authDto.AuthenticationRequest;
import io.github.saifalhaider.nahrain.nahrain_central_api.dto.authDto.AuthenticationResponse;
import io.github.saifalhaider.nahrain.nahrain_central_api.service.auth.AuthenticationService;
import io.github.saifalhaider.nahrain.nahrain_central_api.dto.authDto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@CrossOrigin
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(authenticationService.login(request));
    }
}

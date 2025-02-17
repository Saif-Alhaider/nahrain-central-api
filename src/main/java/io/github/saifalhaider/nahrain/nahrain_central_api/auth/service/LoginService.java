package io.github.saifalhaider.nahrain.nahrain_central_api.auth.service;

import io.github.saifalhaider.nahrain.nahrain_central_api.auth.model.dto.AuthenticationResponseDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.model.dto.LoginRequestDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.model.responseCode.AuthResponseCode;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.jwt.JwtService;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.jwt.RefreshTokenService;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.ApiResponseDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.BaseResponseCode;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.Mapper;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final Mapper<ApiResponseDto.StatusInfo, BaseResponseCode> baseResponseCodeToInfoMapper;
    private final RefreshTokenService refreshTokenService;

    public ResponseEntity<ApiResponseDto<AuthenticationResponseDto>> login(LoginRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        val jwtToken = jwtService.generateAccessToken(user);
        val payload = AuthenticationResponseDto.builder().token(jwtToken).build();
        val statusInfo = baseResponseCodeToInfoMapper.toEntity(AuthResponseCode.REGISTER_SUCCESSFUL);
        ResponseCookie refToken = refreshTokenService.generateRefreshTokenCookie(user);

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, refToken.toString())
                .body(ApiResponseDto.response(statusInfo, payload));
    }
}

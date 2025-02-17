package io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.jwt;


import io.github.saifalhaider.nahrain.nahrain_central_api.auth.Repository.RefreshTokenRepository;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.model.dto.AuthenticationResponseDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.model.entity.RefreshToken;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.model.responseCode.AuthResponseCode;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.exception.InvalidToken;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.ApiResponseDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.BaseResponseCode;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.Mapper;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.User;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.repository.UserRepository;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.util.cookie.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    @Value("${jwt.refresh_token_validity_milliseconds}")
    public long REFRESH_TOKEN_VALIDITY_MS;
    @Value("${cookie.jwt.refresh_token.name}")
    public String REFRESH_TOKEN_COOKIE_NAME;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final Mapper<ApiResponseDto.StatusInfo, BaseResponseCode> baseResponseCodeToInfoMapper;
    private final JwtService jwtService;
    private final CookieUtil cookieUtil;

    public ResponseEntity<ApiResponseDto<AuthenticationResponseDto>> refreshToken(HttpServletRequest request) {
        String refreshToken = cookieUtil.getCookieByName(request, REFRESH_TOKEN_COOKIE_NAME);
        if ((refreshToken != null) && !refreshToken.isEmpty()) {
            return refreshTokenRepository.findByToken(refreshToken)
                    .map(this::verifyExpiration)
                    .map(RefreshToken::getUser)
                    .map(this::generateRefreshTokenResponse)
                    .orElseThrow(() -> new InvalidToken(refreshToken,
                            "Refresh token is not in database"));
        }

        throw new InvalidToken(refreshToken, "Refresh Token is empty or null");
    }

    private ResponseEntity<ApiResponseDto<AuthenticationResponseDto>>
    generateRefreshTokenResponse(User user) {
        ResponseCookie cookie = generateRefreshTokenCookie(user);
        String accessToken = jwtService.generateAccessToken(user);
        AuthenticationResponseDto payload = AuthenticationResponseDto.builder()
                .token(accessToken)
                .build();

        ApiResponseDto.StatusInfo statusInfo = baseResponseCodeToInfoMapper.toEntity(AuthResponseCode.REFRESH_TOKEN_CREATED);

        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(ApiResponseDto.response(statusInfo, payload));
    }

    public ResponseCookie generateRefreshTokenCookie(User user) {
        String refreshToken = createRefreshToken(user).getToken();
        return buildRefreshTokenCookie(refreshToken);
    }

    private ResponseCookie buildRefreshTokenCookie(String refreshToken) {
        return cookieUtil.createHttpOnlyCookie(
                REFRESH_TOKEN_COOKIE_NAME,
                refreshToken,
                "/api/v1/auth/refreshtoken",
                REFRESH_TOKEN_VALIDITY_MS / 1000
        );
    }

    private RefreshToken createRefreshToken(User user) {
        val refToken = refreshTokenRepository.findByUserId(user.getId());

        RefreshToken newRefreshToken;

        if (refToken.isPresent()) {
            newRefreshToken = refToken.get();
            newRefreshToken.setToken(UUID.randomUUID().toString());
            newRefreshToken.setExpiryDate(Instant.now().plusMillis(REFRESH_TOKEN_VALIDITY_MS));

        } else {
            newRefreshToken = RefreshToken.builder()
                    .user(user)
                    .expiryDate(Instant.now().plusMillis(REFRESH_TOKEN_VALIDITY_MS))
                    .token(UUID.randomUUID().toString())
                    .build();
        }

        return refreshTokenRepository.save(newRefreshToken);
    }


    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new InvalidToken(token.getToken(), "Refresh token was expired.");
        }
        return token;
    }

}

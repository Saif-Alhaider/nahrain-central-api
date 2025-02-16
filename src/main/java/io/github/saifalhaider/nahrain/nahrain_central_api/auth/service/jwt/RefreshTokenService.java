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
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.WebUtils;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    @Value("${jwt.refresh-token-validity}")
    public long REFRESH_TOKEN_VALIDITY_MS;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final Mapper<ApiResponseDto.StatusInfo, BaseResponseCode> baseResponseCodeToInfoMapper;
    private final JwtService jwtService;

    public ResponseEntity<ApiResponseDto<AuthenticationResponseDto>> refreshToken(HttpServletRequest request) {
        String refreshToken = getCookieValueByName(request);
        if ((refreshToken != null) && !refreshToken.isEmpty()) {
            return refreshTokenRepository.findByToken(refreshToken)
                    .map(this::verifyExpiration)
                    .map(RefreshToken::getUser)
                    .map(this::getResponse)
                    .orElseThrow(() -> new InvalidToken(refreshToken,
                            "Refresh token is not in database"));
        }

        throw new InvalidToken(refreshToken, "Refresh Token is empty or null");
    }

    private ResponseEntity<ApiResponseDto<AuthenticationResponseDto>> getResponse(User user){
        val refToken = createRefreshToken(user.getId());
        val accessToken = jwtService.generateAccessToken(user);

        val payload = AuthenticationResponseDto.builder().token(accessToken).build();
        val statusInfo = baseResponseCodeToInfoMapper.toEntity(AuthResponseCode.REFRESH_TOKEN_CREATED);
        val refTokenCookie = generateCookie("JWTRefreshCookie", refToken.getToken(), "/api/v1/auth/refreshtoken");

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refTokenCookie.toString())
                .body(ApiResponseDto.response(statusInfo, payload));
    }

    private String getCookieValueByName(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, "JWTRefreshCookie");
        if (cookie != null) {
            return cookie.getValue();
        }
        return null;
    }

    public RefreshToken createRefreshToken(Integer userId) {
        val refToken = refreshTokenRepository.findByUserId(userId);
        val user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        RefreshToken refreshToken;

        if (refToken.isPresent()) {
            refreshToken = refToken.get();
            refreshToken.setToken(UUID.randomUUID().toString());
            refreshToken.setExpiryDate(Instant.now().plusMillis(REFRESH_TOKEN_VALIDITY_MS));

        } else {
            refreshToken = RefreshToken.builder()
                    .user(user)
                    .expiryDate(Instant.now().plusMillis(REFRESH_TOKEN_VALIDITY_MS))
                    .token(UUID.randomUUID().toString())
                    .build();
        }

        return refreshTokenRepository.save(refreshToken); // ✅ Save updated or new token
    }


    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new InvalidToken(token.getToken(), "Refresh token was expired.");
        }

        return token;
    }

    @Transactional
    public int deleteByUserId(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));
        return refreshTokenRepository.deleteByUser(user);
    }

    private ResponseCookie generateCookie(String name, String value, String path) {
        return ResponseCookie.from(name, value).path(path).maxAge(REFRESH_TOKEN_VALIDITY_MS)
                .httpOnly(true).build();
    }
}

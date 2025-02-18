package io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.handler;

import io.github.saifalhaider.nahrain.nahrain_central_api.auth.Repository.RefreshTokenRepository;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.model.entity.RefreshToken;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.User;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.util.cookie.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
public class RefreshTokenHandler {
    @Value("${jwt.refresh_token_validity_milliseconds}")
    public long REFRESH_TOKEN_VALIDITY_MS;
    @Value("${cookie.jwt.refresh_token.name}")
    public String REFRESH_TOKEN_COOKIE_NAME;

    private final CookieUtil cookieUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    public ResponseCookie generateRefreshTokenCookie(User user) {
        String refreshToken = createRefreshToken(user).getToken();
        return buildRefreshTokenCookie(refreshToken);
    }

    private ResponseCookie buildRefreshTokenCookie(String refreshToken) {
        return cookieUtil.createHttpOnlyCookie(
                REFRESH_TOKEN_COOKIE_NAME,
                refreshToken,
                "/api/v1/auth/refreshtoken",//todo modify to use dynamic value instead of static
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
}

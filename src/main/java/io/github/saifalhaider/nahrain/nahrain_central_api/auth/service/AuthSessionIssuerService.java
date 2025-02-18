package io.github.saifalhaider.nahrain.nahrain_central_api.auth.service;

import io.github.saifalhaider.nahrain.nahrain_central_api.auth.model.entity.AuthIssue;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.handler.JwtAccessTokenHandler;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.handler.RefreshTokenHandler;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthSessionIssuerService {
    private final JwtAccessTokenHandler jwtAccessTokenHandler;
    private final RefreshTokenHandler refreshTokenHandler;

    public AuthIssue generateNewAuthToken(User user) {
        ResponseCookie cookie = refreshTokenHandler.generateRefreshTokenCookie(user);
        String accessToken = jwtAccessTokenHandler.generateAccessToken(user);

        return AuthIssue.builder().refreshToken(cookie).token(accessToken).build();
    }

}

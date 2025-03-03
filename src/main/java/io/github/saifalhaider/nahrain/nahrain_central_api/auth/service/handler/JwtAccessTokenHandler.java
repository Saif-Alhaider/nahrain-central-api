package io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.handler;

import io.github.saifalhaider.nahrain.nahrain_central_api.auth.config.JwtConfig;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

@RequiredArgsConstructor
public class JwtAccessTokenHandler {
    @Value("${jwt.access_token_validity_milliseconds}")
    private long ACCESS_TOKEN_VALIDITY;
    @Value("${jwt.refresh_token_validity_milliseconds}")
    public long REFRESH_TOKEN_VALIDITY_MS;
    private final JwtConfig jwtConfig;

    public String generateAccessToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("role", userDetails.getAuthorities())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY))
                .signWith(jwtConfig.getSignInKey())
                .compact();
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("role", userDetails.getAuthorities())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY_MS))
                .signWith(jwtConfig.getSignInKey())
                .compact();
    }
}

package io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.jwt;

import io.github.saifalhaider.nahrain.nahrain_central_api.auth.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@RequiredArgsConstructor
public class JwtHelperImpl implements JwtHelper {
    private final JwtConfig jwtConfig;

    @Override
    public String getUserNameClaim(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(jwtConfig.getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}

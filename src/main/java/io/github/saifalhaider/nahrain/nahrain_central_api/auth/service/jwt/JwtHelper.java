package io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.jwt;

import io.jsonwebtoken.Claims;

import java.util.function.Function;

public interface JwtHelper {
    String getUserNameClaim(String token);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    Claims extractAllClaims(String token);
}


package io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.jwt;

import io.jsonwebtoken.Claims;

import java.util.Date;
import java.util.function.Function;

public interface JwtHelper {
  String getUserNameClaim(String token);

  Date getExpirationClaim(String token);

  <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

  Claims extractAllClaims(String token);
}

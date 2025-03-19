package io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.validation.jwt;

import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.jwt.JwtHelper;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

@RequiredArgsConstructor
public class JwtValidatorImpl implements JwtValidator {
  private final JwtHelper jwtHelper;

  @Override
  public boolean isJwtValid(String token, UserDetails userDetails) {
    final String username = jwtHelper.getUserNameClaim(token);
    return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    return jwtHelper.extractClaim(token, Claims::getExpiration).before(new Date());
  }
}

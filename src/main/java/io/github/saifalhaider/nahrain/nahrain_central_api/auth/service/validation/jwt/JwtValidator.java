package io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.validation.jwt;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtValidator {
  boolean isJwtValid(String token, UserDetails userDetails);
}

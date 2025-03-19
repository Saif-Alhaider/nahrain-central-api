package io.github.saifalhaider.nahrain.nahrain_central_api.common.exceptions;

public class JwtAuthenticationException extends RuntimeException {
  public JwtAuthenticationException(String message) {
    super(message);
  }
}

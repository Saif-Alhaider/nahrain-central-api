package io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.exception;

public class InvalidToken extends RuntimeException {
  public InvalidToken(String authToken, String message) {
    super(authToken + " " + message);
  }
}

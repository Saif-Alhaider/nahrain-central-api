package io.github.saifalhaider.nahrain.nahrain_central_api.totp.controller.Exceptions;

public class IncorrectTotp extends RuntimeException {
  public IncorrectTotp(String message) {
    super(message);
  }
}

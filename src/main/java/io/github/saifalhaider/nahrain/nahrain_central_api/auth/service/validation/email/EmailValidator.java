package io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.validation.email;

public interface EmailValidator {
  boolean isValid(String email);

  String getCompletedEmail(String email);
}

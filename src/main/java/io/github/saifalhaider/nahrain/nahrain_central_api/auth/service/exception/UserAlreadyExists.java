package io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.exception;

/**
 * Exception thrown to indicate that a user already exists when attempting to create or register a
 * new user.
 *
 * <p>This exception typically signals a conflict in user registration or any operation where a
 * unique user constraint is violated.
 *
 * <p>Extends the AuthException class to provide specific handling for user existence-related
 * authentication errors.
 */
public class UserAlreadyExists extends AuthException {
  public UserAlreadyExists(String message) {
    super(message);
  }
}

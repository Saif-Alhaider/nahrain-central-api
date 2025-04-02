package io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user;

import jakarta.annotation.Nullable;

public class UserFactory {
  public static User createUser(
      @Nullable String fullName,
      String email,
      @Nullable String password,
      @Nullable User.Role role,
      @Nullable Boolean mfaEnabled,
      @Nullable String totpSecret) {
    if (role == null) {
      return PendingUser.builder()
          .fullName(fullName)
          .email(email)
          .password(password)
          .mfaEnabled(Boolean.TRUE.equals(mfaEnabled))
          .totpSecret(totpSecret)
          .build();
    }
    return switch (role) {
      case PROF ->
          Prof.builder()
              .fullName(fullName)
              .email(email)
              .password(password)
              .mfaEnabled(Boolean.TRUE.equals(mfaEnabled))
              .totpSecret(totpSecret)
              .build();
      case STUDENT ->
          Student.builder()
              .fullName(fullName)
              .email(email)
              .password(password)
              .mfaEnabled(Boolean.TRUE.equals(mfaEnabled))
              .totpSecret(totpSecret)
              .build();
      case ADMIN ->
          Admin.builder()
              .fullName(fullName)
              .email(email)
              .password(password)
              .mfaEnabled(Boolean.TRUE.equals(mfaEnabled))
              .totpSecret(totpSecret)
              .build();
    };
  }
}

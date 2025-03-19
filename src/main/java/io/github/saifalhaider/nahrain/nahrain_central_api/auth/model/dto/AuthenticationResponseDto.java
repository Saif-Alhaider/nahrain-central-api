package io.github.saifalhaider.nahrain.nahrain_central_api.auth.model.dto;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponseDto {
  private String token;
  private String refreshToken;
  private boolean mfaEnabled;
  private UserDto user;
}

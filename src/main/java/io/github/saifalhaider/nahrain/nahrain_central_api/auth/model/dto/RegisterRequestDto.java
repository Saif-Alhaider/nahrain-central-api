package io.github.saifalhaider.nahrain.nahrain_central_api.auth.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDto {
  private String password;
  private String email;
  private String fullName;
}

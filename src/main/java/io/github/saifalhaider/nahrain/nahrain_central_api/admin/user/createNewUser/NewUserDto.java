package io.github.saifalhaider.nahrain.nahrain_central_api.admin.user.createNewUser;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.GenderDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.User;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewUserDto {
  private String fullName;
  private LocalDate birthDate;
  private String email;
  private GenderDto gender;
  private String phoneNumber;
  private String province;
  private User.Role role;
}

package io.github.saifalhaider.nahrain.nahrain_central_api.admin.SetPendingUserRole;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoleDto {
  private User.Role role;
}

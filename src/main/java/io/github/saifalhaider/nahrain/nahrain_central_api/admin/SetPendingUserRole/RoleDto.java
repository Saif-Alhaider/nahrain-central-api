package io.github.saifalhaider.nahrain.nahrain_central_api.admin.SetPendingUserRole;

import io.github.saifalhaider.nahrain.nahrain_central_api.admin.SetPendingUserRole.exceptions.InvalidRoleAssignment;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.stage.Stage;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.User;

public record RoleDto(User.Role role, Stage.StageType stage) {
  public RoleDto {
    if (role == User.Role.STUDENT && stage == null) {
      throw new InvalidRoleAssignment("Stage selection is required for students");
    }
  }
}

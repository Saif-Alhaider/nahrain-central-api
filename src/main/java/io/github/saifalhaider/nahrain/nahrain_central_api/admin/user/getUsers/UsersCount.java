package io.github.saifalhaider.nahrain.nahrain_central_api.admin.user.getUsers;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsersCount {
  private final Long studentsCount;
  private final Long profsCount;
}

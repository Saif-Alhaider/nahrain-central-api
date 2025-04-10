package io.github.saifalhaider.nahrain.nahrain_central_api.admin.user.getUsers;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.UserDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UsersDto<T extends UserDto> {
  private List<T> users;
  private int totalPages;
  private long totalNumberOfUsers;
}

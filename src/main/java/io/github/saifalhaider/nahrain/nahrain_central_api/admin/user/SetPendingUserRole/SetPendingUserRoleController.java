package io.github.saifalhaider.nahrain.nahrain_central_api.admin.user.SetPendingUserRole;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.ApiResponseDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/admin")
@RequiredArgsConstructor
public class SetPendingUserRoleController {
  private final SetPendingUserRoleService setPendingUserRoleService;

  @PutMapping("/users/pending/{id}")
  public ApiResponseDto<UserDto> getStudents(
      @PathVariable Integer id, @RequestBody RoleDto roleDto) {
    return setPendingUserRoleService.updatePendingUserRole(id, roleDto.role(), roleDto.stage());
  }
}

package io.github.saifalhaider.nahrain.nahrain_central_api.admin.user.getUsers;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.ApiResponseDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.AdminDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.PendingUserDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.ProfDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.StudentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/admin")
@RequiredArgsConstructor
public class GetUsersController {
  private final GetUsersService getUsersService;

  @GetMapping("/students")
  public ApiResponseDto<UsersDto<StudentDto>> getStudents(
      @RequestParam(defaultValue = "0", required = false) int pageNumber,
      @RequestParam(defaultValue = "1", required = false) int pageSize) {
    return getUsersService.getStudents(pageNumber, pageSize);
  }

  @GetMapping("/admins")
  public ApiResponseDto<UsersDto<AdminDto>> getAdmins(
      @RequestParam(defaultValue = "0", required = false) int pageNumber,
      @RequestParam(defaultValue = "1", required = false) int pageSize) {
    return getUsersService.getAdmins(pageNumber, pageSize);
  }

  @GetMapping("/profs")
  public ApiResponseDto<UsersDto<ProfDto>> getProfs(
      @RequestParam(defaultValue = "0", required = false) int pageNumber,
      @RequestParam(defaultValue = "1", required = false) int pageSize) {
    return getUsersService.getProfs(pageNumber, pageSize);
  }

  @GetMapping("/pending-users")
  public ApiResponseDto<UsersDto<PendingUserDto>> getPendingUsers(
      @RequestParam(defaultValue = "0", required = false) int pageNumber,
      @RequestParam(defaultValue = "1", required = false) int pageSize) {
    return getUsersService.getPendingUsers(pageNumber, pageSize);
  }

  @GetMapping("/count")
  public ApiResponseDto<UsersCount> getUsersCount() {
    UsersCount usersCount = getUsersService.getUsersCount();
    return ApiResponseDto.response(
        ApiResponseDto.StatusInfo.builder()
            .code(HttpStatus.OK.value())
            .message("users count retrieved successfully")
            .build(),
        usersCount);
  }
}

package io.github.saifalhaider.nahrain.nahrain_central_api.admin.SetPendingUserRole;

import io.github.saifalhaider.nahrain.nahrain_central_api.auth.model.responseCode.AuthResponseCode;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.ApiResponseDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.AdminDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.ProfDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.StudentDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.UserDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.stage.Stage;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.*;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.repository.stage.StageRepository;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.repository.user.PendingUserRepository;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.repository.user.UserRepository;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.repository.user.UserRepositoryFactory;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Transactional
public class SetPendingUserRoleService {
  private final PendingUserRepository pendingUserRepo;
  private final StageRepository stageRepo;
  private final UserRepositoryFactory userRepositoryFactory;

  public ApiResponseDto<UserDto> updatePendingUserRole(
      Integer userId, User.Role role, Stage.StageType stageType) {
    PendingUser pendingUser = findPendingUserOrThrow(userId);

    User newUser = convertToUser(pendingUser, role);
    User savedUser = saveUserByRole(newUser, stageType);
    pendingUserRepo.delete(pendingUser);

    return buildSuccessResponse(savedUser);
  }

  private PendingUser findPendingUserOrThrow(Integer userId) {
    return pendingUserRepo
        .findById(userId)
        .orElseThrow(
            () -> new UsernameNotFoundException("User with id %d not found".formatted(userId)));
  }

  private User convertToUser(PendingUser pendingUser, User.Role role) {
    return UserFactory.createUser(
        pendingUser.getFullName(),
        pendingUser.getEmail(),
        pendingUser.getPassword(),
        role,
        pendingUser.isMfaEnabled(),
        pendingUser.getTotpSecret());
  }

  private User saveUserByRole(User user, Stage.StageType stageType) {
    if (user instanceof Student student) {
      Stage stage = stageRepo.findByStageType(stageType).orElseThrow();
      student.setStage(stage);
    }
    return getTypeSafeRepository(user).save(user);
  }

  private ApiResponseDto<UserDto> buildSuccessResponse(User user) {
    return ApiResponseDto.<UserDto>builder()
        .info(
            ApiResponseDto.StatusInfo.builder()
                .code(AuthResponseCode.PENDING_ROLE_ASSIGNMENT.getCode())
                .message(AuthResponseCode.PENDING_ROLE_ASSIGNMENT.getMessage())
                .build())
        .payload(convertToDto(user))
        .build();
  }

  @SuppressWarnings("unchecked")
  private <T extends User> UserRepository<T, Integer> getTypeSafeRepository(User user) {
    return (UserRepository<T, Integer>) userRepositoryFactory.getRepository(user.getClass());
  }

  // TODO Create a Mapper Class
  private UserDto convertToDto(User user) {
    if (user instanceof Admin admin) {
      return AdminDto.builder()
          .id(admin.getId())
          .date(admin.getCreatedDate())
          .fullName(admin.getFullName())
          .email(admin.getEmail())
          .build();
    } else if (user instanceof Prof prof) {
      return ProfDto.builder()
          .id(prof.getId())
          .date(prof.getCreatedDate())
          .fullName(prof.getFullName())
          .email(prof.getEmail())
          .build();
    } else if (user instanceof Student student) {
      return StudentDto.builder()
          .id(student.getId())
          .date(student.getCreatedDate())
          .fullName(student.getFullName())
          .email(student.getEmail())
          .build();
    }
    throw new IllegalArgumentException("Unknown user type: " + user.getClass());
  }
}

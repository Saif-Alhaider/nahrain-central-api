package io.github.saifalhaider.nahrain.nahrain_central_api.common.repository.user;

import io.github.saifalhaider.nahrain.nahrain_central_api.admin.user.SetPendingUserRole.exceptions.InvalidRoleAssignment;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.Admin;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.Prof;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.Student;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRepositoryFactory {
  private final AdminRepository adminRepo;
  private final ProfRepository profRepo;
  private final StudentRepository studentRepo;

  @SuppressWarnings("unchecked")
  public <T extends User> UserRepository<T, Integer> getRepository(T user) {
    if (user instanceof Admin) {
      return (UserRepository<T, Integer>) adminRepo;
    } else if (user instanceof Prof) {
      return (UserRepository<T, Integer>) profRepo;
    } else if (user instanceof Student) {
      return (UserRepository<T, Integer>) studentRepo;
    }
    throw new InvalidRoleAssignment("Unknown user type: " + user.getClass().getSimpleName());
  }
}

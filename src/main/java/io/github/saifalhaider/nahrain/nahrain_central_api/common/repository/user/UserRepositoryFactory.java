package io.github.saifalhaider.nahrain.nahrain_central_api.common.repository.user;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.Admin;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.Prof;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.Student;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRepositoryFactory {
  private final AdminRepository adminRepo;
  private final ProfRepository profRepo;
  private final StudentRepository studentRepo;

  @SuppressWarnings("unchecked")
  public <T extends User> UserRepository<T, Integer> getRepository(Class<T> userClass) {
    if (Admin.class.isAssignableFrom(userClass)) {
      return (UserRepository<T, Integer>) adminRepo;
    } else if (Prof.class.isAssignableFrom(userClass)) {
      return (UserRepository<T, Integer>) profRepo;
    } else if (Student.class.isAssignableFrom(userClass)) {
      return (UserRepository<T, Integer>) studentRepo;
    }
    throw new IllegalArgumentException("Unsupported user type: " + userClass.getName());
  }
}

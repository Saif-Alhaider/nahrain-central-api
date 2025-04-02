package io.github.saifalhaider.nahrain.nahrain_central_api.common.repository.user;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.Student;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends UserRepository<Student, Integer> {}

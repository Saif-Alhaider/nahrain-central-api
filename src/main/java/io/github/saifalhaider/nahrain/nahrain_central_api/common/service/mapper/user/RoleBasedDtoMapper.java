package io.github.saifalhaider.nahrain.nahrain_central_api.common.service.mapper.user;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.UserDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.Admin;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.Prof;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.Student;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleBasedDtoMapper {
  private final AdminDtoMapper adminDtoMapper;
  private final ProfDtoMapper profDtoMapper;
  private final StudentDtoMapper studentDtoMapper;

  public UserDto mapToDto(User user) {
    if (user instanceof Admin admin) {
      return adminDtoMapper.mapTo(admin);
    } else if (user instanceof Prof prof) {
      return profDtoMapper.mapTo(prof);
    } else if (user instanceof Student student) {
      return studentDtoMapper.mapTo(student);
    } else {
      return null;
    }
  }
}

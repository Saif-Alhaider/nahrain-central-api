package io.github.saifalhaider.nahrain.nahrain_central_api.common.service.mapper;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.Mapper;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.AdminDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.Admin;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminDtoMapper implements Mapper<AdminDto, Admin> {

  @Override
  public AdminDto mapToDomain(Admin admin) {
    return AdminDto.builder()
        .id(admin.getId())
        .email(admin.getEmail())
        .date(admin.getCreatedDate())
        .fullName(admin.getFullName())
        .build();
  }

  @Override
  public List<AdminDto> mapToDomainList(List<Admin> admins) {
    return admins.stream().map(this::mapToDomain).toList();
  }
}

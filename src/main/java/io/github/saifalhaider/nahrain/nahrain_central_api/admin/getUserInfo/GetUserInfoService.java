package io.github.saifalhaider.nahrain.nahrain_central_api.admin.getUserInfo;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.ApiResponseDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.UserDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.User;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.repository.user.UserRepository;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.service.mapper.RoleBasedDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetUserInfoService {
  private final UserRepository<User, Integer> userRepository;
  private final RoleBasedDtoMapper roleBasedDtoMapper;

  public ResponseEntity<ApiResponseDto<? extends UserDto>> getUserInfo(Integer id) {
    User user =
        userRepository
            .findById(id)
            .orElseThrow(() -> new UsernameNotFoundException("User with id" + id + " not found"));
    ApiResponseDto<? extends UserDto> response =
        ApiResponseDto.response(
            ApiResponseDto.StatusInfo.builder().code(200).message("Success").build(),
            roleBasedDtoMapper.mapToDto(user));

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}

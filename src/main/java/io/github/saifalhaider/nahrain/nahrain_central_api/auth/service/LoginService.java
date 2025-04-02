package io.github.saifalhaider.nahrain.nahrain_central_api.auth.service;

import io.github.saifalhaider.nahrain.nahrain_central_api.auth.model.dto.AuthenticationResponseDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.model.dto.LoginRequestDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.model.responseCode.AuthResponseCode;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.handler.JwtAccessTokenHandler;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.validation.email.EmailValidator;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.ApiResponseDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.BaseResponseCode;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.Mapper;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.User;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.repository.user.UserRepository;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.service.mapper.RoleBasedDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
  private final AuthenticationManager authenticationManager;
  private final UserRepository<User, Integer> userRepository;
  private final Mapper<ApiResponseDto.StatusInfo, BaseResponseCode> baseResponseCodeToInfoMapper;
  private final JwtAccessTokenHandler jwtAccessTokenHandler;
  private final EmailValidator emailValidator;
  private final RoleBasedDtoMapper roleBasedDtoMapper;

  public ResponseEntity<ApiResponseDto<AuthenticationResponseDto>> login(LoginRequestDto request) {
    request.setEmail(emailValidator.getCompletedEmail(request.getEmail()));
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

    var user =
        userRepository
            .findByEmail(request.getEmail())
            .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

    val accessToken = jwtAccessTokenHandler.generateAccessToken(user);
    val refreshToken = jwtAccessTokenHandler.generateRefreshToken(user);

    val statusInfo = baseResponseCodeToInfoMapper.mapToDomain(AuthResponseCode.LOGIN_SUCCESSFUL);
    val payload =
        AuthenticationResponseDto.builder()
            .token(accessToken)
            .refreshToken(refreshToken)
            .mfaEnabled(user.isMfaEnabled())
            .user(roleBasedDtoMapper.mapToDto(user))
            .build();

    return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDto.response(statusInfo, payload));
  }
}

package io.github.saifalhaider.nahrain.nahrain_central_api.auth.service;

import io.github.saifalhaider.nahrain.nahrain_central_api.auth.model.dto.AuthenticationResponseDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.model.dto.RefreshTokenDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.model.responseCode.AuthResponseCode;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.exception.InvalidToken;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.handler.JwtAccessTokenHandler;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.jwt.JwtHelper;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.ApiResponseDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.BaseResponseCode;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.Mapper;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.User;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.repository.user.UserRepository;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.service.mapper.user.RoleBasedDtoMapper;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
  private final Mapper<ApiResponseDto.StatusInfo, BaseResponseCode> baseResponseCodeToInfoMapper;
  private final JwtAccessTokenHandler jwtAccessTokenHandler;
  private final UserRepository<User, Integer> userRepository;
  private final JwtHelper jwtHelper;
  private final RoleBasedDtoMapper roleBasedDtoMapper;

  public ResponseEntity<ApiResponseDto<AuthenticationResponseDto>> refreshToken(
      RefreshTokenDto request) {
    if (!isRefreshTokenExpired(request.getRefreshToken())) {
      return handleRefreshToken(request);
    } else {
      throw new InvalidToken("", "Refresh Token is Expired");
    }
  }

  private boolean isRefreshTokenExpired(String refreshToken) {
    return jwtHelper.getExpirationClaim(refreshToken).before(new Date());
  }

  private ResponseEntity<ApiResponseDto<AuthenticationResponseDto>> handleRefreshToken(
      RefreshTokenDto request) {
    val userEmail = jwtHelper.getUserNameClaim(request.getRefreshToken());

    val user = userRepository.findByEmail(userEmail);
    try {
      if (user.isPresent()) {
        val accessToken = jwtAccessTokenHandler.generateAccessToken(user.get());
        val refreshToken = jwtAccessTokenHandler.generateRefreshToken(user.get());
        val payload =
            AuthenticationResponseDto.builder()
                .token(accessToken)
                .refreshToken(refreshToken)
                .mfaEnabled(user.get().isMfaEnabled())
                .user(roleBasedDtoMapper.mapToDto(user.get()))
                .build();
        ApiResponseDto.StatusInfo statusInfo =
            baseResponseCodeToInfoMapper.mapTo(AuthResponseCode.REFRESH_TOKEN_CREATED);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponseDto.response(statusInfo, payload));
      } else {
        throw new UsernameNotFoundException("User Not Found");
      }
    } catch (Exception e) {
      throw new InvalidToken("", "Refresh Token is not valid");
    }
  }
}

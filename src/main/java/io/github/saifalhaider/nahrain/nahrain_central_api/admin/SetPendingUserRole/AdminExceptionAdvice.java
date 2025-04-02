package io.github.saifalhaider.nahrain.nahrain_central_api.admin.SetPendingUserRole;

import io.github.saifalhaider.nahrain.nahrain_central_api.admin.SetPendingUserRole.exceptions.InvalidRoleAssignment;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.model.responseCode.AuthResponseCode;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.ApiResponseDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.BaseExceptionAdvice;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.BaseResponseCode;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.Mapper;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AdminExceptionAdvice extends BaseExceptionAdvice {

  public AdminExceptionAdvice(
      Mapper<ApiResponseDto.StatusInfo, BaseResponseCode> baseResponseCodeToInfoMapper) {
    super(baseResponseCodeToInfoMapper);
  }

  @ExceptionHandler({InvalidRoleAssignment.class})
  public ResponseEntity<ApiResponseDto<Map<String, Object>>> handleEmailNotValidException(
      RuntimeException err) {
    return handleException(err, HttpStatus.BAD_REQUEST, AuthResponseCode.INVALID_ROLE_ASSIGNMENT);
  }
}

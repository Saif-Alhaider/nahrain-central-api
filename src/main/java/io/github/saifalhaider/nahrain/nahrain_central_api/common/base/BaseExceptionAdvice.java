package io.github.saifalhaider.nahrain.nahrain_central_api.common.base;

import io.github.saifalhaider.nahrain.nahrain_central_api.auth.model.responseCode.AuthResponseCode;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public abstract class BaseExceptionAdvice {
  private final Mapper<ApiResponseDto.StatusInfo, BaseResponseCode> baseResponseCodeToInfoMapper;

  protected ResponseEntity<ApiResponseDto<Map<String, Object>>> handleException(
      RuntimeException err, HttpStatus status, AuthResponseCode responseCode) {
    Map<String, Object> errorResponse =
        Map.of(
            "timestamp", LocalDateTime.now(),
            "status", status.value(),
            "error", status.getReasonPhrase(),
            "message", err.getMessage());
    val payload =
        ApiResponseDto.response(
            baseResponseCodeToInfoMapper.mapTo(responseCode), errorResponse);

    return ResponseEntity.status(status).body(payload);
  }
}

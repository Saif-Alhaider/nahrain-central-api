package io.github.saifalhaider.nahrain.nahrain_central_api.common.base;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class ApiResponseDto<T> {
  private final StatusInfo info;
  private final T payload;

  public static <T> ApiResponseDto<T> response(StatusInfo info, T payload) {
    return new ApiResponseDto<>(info, payload);
  }

  @Getter
  @Builder
  public static class StatusInfo {
    final Integer code;
    final String message;
  }
}

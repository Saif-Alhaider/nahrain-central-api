package io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageDto {
  private final String message;
}

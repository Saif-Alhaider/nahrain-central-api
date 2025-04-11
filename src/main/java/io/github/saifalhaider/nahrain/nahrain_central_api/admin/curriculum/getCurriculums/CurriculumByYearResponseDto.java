package io.github.saifalhaider.nahrain.nahrain_central_api.admin.curriculum.getCurriculums;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.CurriculumDto;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CurriculumByYearResponseDto {
  private final CurriculumYearDto firstYear;
  private final CurriculumYearDto secondYear;
  private final CurriculumYearDto thirdYear;
  private final CurriculumYearDto finalYear;

  @Getter
  @Builder
  public static class CurriculumYearDto {
    private int size;
    private List<CurriculumDto> items;
  }
}

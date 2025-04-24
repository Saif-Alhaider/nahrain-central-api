package io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.stage.StageType;
import java.util.List;
import lombok.*;

@Data
@AllArgsConstructor
@Builder
public class CurriculumDto {
  Long id;
  String name;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  StageType stageType;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  Type type;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  List<String> resources;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  List<ProfDto> profs;

  public enum Type {
    THEORETICAL,
    LAB,
    THEORETICAL_AND_LAB,
  }
}

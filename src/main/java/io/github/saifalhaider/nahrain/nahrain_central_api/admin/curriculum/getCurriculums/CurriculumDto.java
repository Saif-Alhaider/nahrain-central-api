package io.github.saifalhaider.nahrain.nahrain_central_api.admin.curriculum.getCurriculums;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.ProfDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.stage.StageType;
import java.util.List;
import lombok.*;

@Data
@AllArgsConstructor
@Builder
public class CurriculumDto {
  Long id;
  String name;
  StageType stageType;
  Type type;
  List<String> resources;
  List<ProfDto> profs;

  public enum Type {
    THEORETICAL,
    LAB,
    THEORETICAL_AND_LAB,
  }
}

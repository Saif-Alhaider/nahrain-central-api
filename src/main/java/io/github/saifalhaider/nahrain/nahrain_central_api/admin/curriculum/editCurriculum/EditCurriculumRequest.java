package io.github.saifalhaider.nahrain.nahrain_central_api.admin.curriculum.editCurriculum;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.curriculum.Curriculum;
import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditCurriculumRequest {
  private Long id;

  private Curriculum.Type type;

  private String name;

  private List<String> resources;

  private Long stageId;

  private List<Integer> profIds;
}

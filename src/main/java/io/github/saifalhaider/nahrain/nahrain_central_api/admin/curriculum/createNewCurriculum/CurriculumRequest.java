package io.github.saifalhaider.nahrain.nahrain_central_api.admin.curriculum.createNewCurriculum;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.curriculum.Curriculum;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.stage.StageType;
import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurriculumRequest {
    private String name;
    private StageType stageType;
    private Curriculum.Type curriculumType;
    private List<Integer> profsIds;
    private List<String> resources;
}

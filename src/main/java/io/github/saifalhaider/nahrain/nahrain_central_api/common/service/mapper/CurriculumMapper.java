package io.github.saifalhaider.nahrain.nahrain_central_api.common.service.mapper;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.CurriculumDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.Mapper;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.curriculum.Curriculum;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.stage.Stage;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.service.mapper.user.ProfMapper;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurriculumMapper implements Mapper<Curriculum, CurriculumDto> {
  private final StageMapper stageMapper;
  private final ProfMapper profMapper;

  @Override
  public Curriculum mapTo(CurriculumDto dto) {
    Stage stage = stageMapper.mapTo(dto.getStageType());
    return Curriculum.customBuilder()
        .name(dto.getName())
        .stage(stage)
        .type(toCurriculumType(dto.getType()))
        .resourcesList(dto.getResources())
        .profs(profMapper.mapToList(dto.getProfs()))
        .build();
  }

  @Override
  public List<Curriculum> mapToList(List<CurriculumDto> dtoList) {
    return dtoList.stream().map(this::mapTo).collect(Collectors.toList());
  }

  private Curriculum.Type toCurriculumType(CurriculumDto.Type dtoType) {
    return switch (dtoType) {
      case THEORETICAL -> Curriculum.Type.THEORETICAL;
      case LAB -> Curriculum.Type.LAB;
      case THEORETICAL_AND_LAB -> Curriculum.Type.THEORETICAL_AND_LAB;
    };
  }
}

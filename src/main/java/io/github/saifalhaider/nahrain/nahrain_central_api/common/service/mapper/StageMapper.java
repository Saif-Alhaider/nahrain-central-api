package io.github.saifalhaider.nahrain.nahrain_central_api.common.service.mapper;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.Mapper;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.stage.Stage;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.stage.StageType;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.repository.stage.StageRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StageMapper implements Mapper<Stage, StageType> {
  private final StageRepository stageRepository;

  @Override
  public Stage mapTo(StageType stageType) {
    return stageRepository
        .findByStageType(stageType)
        .orElseThrow(() -> new EntityNotFoundException("Stage not found for type: " + stageType));
  }

  @Override
  public List<Stage> mapToList(List<StageType> dtoList) {
    return List.of();
  }
}

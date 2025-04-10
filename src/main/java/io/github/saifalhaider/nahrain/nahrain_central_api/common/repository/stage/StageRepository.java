package io.github.saifalhaider.nahrain.nahrain_central_api.common.repository.stage;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.stage.Stage;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.stage.StageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StageRepository extends JpaRepository<Stage, Long> {
  Optional<Stage> findByStageType(StageType stageType);
}

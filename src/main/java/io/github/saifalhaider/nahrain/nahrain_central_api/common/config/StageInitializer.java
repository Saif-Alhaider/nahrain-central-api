package io.github.saifalhaider.nahrain.nahrain_central_api.common.config;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.stage.Stage;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.repository.stage.StageRepository;
import java.util.Arrays;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class StageInitializer implements CommandLineRunner {

  private final StageRepository stageRepository;

  public StageInitializer(StageRepository stageRepository) {
    this.stageRepository = stageRepository;
  }

  @Override
  @Transactional
  public void run(String... args) {
    if (stageRepository.count() == 0) {
      Arrays.stream(Stage.StageType.values())
          .map(stageType -> Stage.builder().stageType(stageType).build())
          .forEach(stageRepository::save);
    }
  }
}

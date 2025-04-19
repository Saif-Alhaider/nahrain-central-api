package io.github.saifalhaider.nahrain.nahrain_central_api.admin.curriculum.editCurriculum;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.CurriculumDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.curriculum.Curriculum;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.stage.Stage;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.Prof;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.repository.CurriculumRepository;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.repository.stage.StageRepository;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.repository.user.ProfRepository;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.service.mapper.CurriculumDtoMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EditCurriculumService {
  private final CurriculumRepository curriculumRepository;
  private final StageRepository stageRepository;
  private final ProfRepository profRepository;
  private final CurriculumDtoMapper curriculumDtoMapper;

  @Transactional
  public CurriculumDto updateCurriculum(EditCurriculumRequest request) {
    Curriculum curriculum =
        curriculumRepository
            .findById(request.getId())
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        "Curriculum not found with id: " + request.getId()));

    curriculum.setType(request.getType());
    curriculum.setName(request.getName());
    curriculum.setResourcesList(request.getResources());

    if (!curriculum.getStage().getId().equals(request.getStageId())) {
      Stage newStage =
          stageRepository
              .findById(request.getStageId())
              .orElseThrow(() -> new EntityNotFoundException("Stage not found"));
      curriculum.setStage(newStage);
    }

    List<Prof> professors = profRepository.findAllById(request.getProfIds());
    if (professors.size() != request.getProfIds().size()) {
      throw new EntityNotFoundException("One or more professors not found");
    }
    curriculum.setProfs(professors);

    Curriculum savedCurriculum = curriculumRepository.save(curriculum);
    return curriculumDtoMapper.mapTo(savedCurriculum);
  }
}

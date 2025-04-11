package io.github.saifalhaider.nahrain.nahrain_central_api.admin.curriculum.createNewCurriculum;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.CurriculumDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.curriculum.Curriculum;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.stage.Stage;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.Prof;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.repository.CurriculumRepository;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.repository.stage.StageRepository;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.repository.user.ProfRepository;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.service.mapper.CurriculumDtoMapper;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.service.mapper.CurriculumMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateNewCurriculumService {
  private final CurriculumRepository curriculumRepository;
  private final CurriculumMapper curriculumMapper;
  private final ProfRepository profRepository;
  private final StageRepository stageRepository;
  private final CurriculumDtoMapper curriculumDtoMapper;

  @Transactional
  public CurriculumDto createCurriculum(CurriculumRequest request) {
    Stage stage =
        stageRepository
            .findByStageType(request.getStageType())
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        "Stage not found with type: " + request.getStageType()));

    List<Prof> profs = Collections.emptyList();
    if (request.getProfsIds() != null && !request.getProfsIds().isEmpty()) {
      profs = profRepository.findAllById(request.getProfsIds());

      if (profs.size() != request.getProfsIds().size()) {
        Set<Integer> foundIds = profs.stream().map(Prof::getId).collect(Collectors.toSet());
        List<Integer> missingIds =
            request.getProfsIds().stream().filter(id -> !foundIds.contains(id)).toList();
        throw new EntityNotFoundException("Professors not found with IDs: " + missingIds);
      }
    }

    Curriculum curriculum =
        Curriculum.customBuilder()
            .name(request.getName())
            .stage(stage)
            .type(request.getCurriculumType())
            .profs(profs)
            .resourcesList(request.getResources())
            .build();

    Curriculum savedCurriculum = curriculumRepository.save(curriculum);
    return curriculumDtoMapper.mapTo(savedCurriculum);
  }
}

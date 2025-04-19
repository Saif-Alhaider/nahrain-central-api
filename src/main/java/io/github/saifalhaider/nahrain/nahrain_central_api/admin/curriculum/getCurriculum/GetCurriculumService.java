package io.github.saifalhaider.nahrain.nahrain_central_api.admin.curriculum.getCurriculum;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.CurriculumDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.curriculum.Curriculum;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.repository.CurriculumRepository;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.service.mapper.CurriculumDtoMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetCurriculumService {
  private final CurriculumRepository curriculumRepository;
  private final CurriculumDtoMapper curriculumDtoMapper;

  public CurriculumDto getCurriculum(Long id) {
    Curriculum curriculum =
        curriculumRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Curriculum not found with id: " + id));

    return curriculumDtoMapper.mapTo(curriculum);
  }
}

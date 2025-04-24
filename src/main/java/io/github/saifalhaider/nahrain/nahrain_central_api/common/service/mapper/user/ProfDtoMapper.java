package io.github.saifalhaider.nahrain.nahrain_central_api.common.service.mapper.user;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.Mapper;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.CurriculumDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.ProfDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.Prof;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.repository.CurriculumRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfDtoMapper implements Mapper<ProfDto, Prof> {
  private final CurriculumRepository curriculumRepository;

  @Override
  public ProfDto mapTo(Prof prof) {
    List<CurriculumDto> profCurriculums =
        curriculumRepository.findByProfsContaining(prof).stream()
            .map(
                curriculum ->
                    CurriculumDto.builder()
                        .id(curriculum.getId())
                        .name(curriculum.getName())
                        .build())
            .toList();

    return ProfDto.builder()
        .id(prof.getId())
        .email(prof.getEmail())
        .date(prof.getCreatedDate())
        .fullName(prof.getFullName())
        .curriculums(profCurriculums)
        .build();
  }

  @Override
  public List<ProfDto> mapToList(List<Prof> profs) {
    return profs.stream().map(this::mapTo).toList();
  }
}

package io.github.saifalhaider.nahrain.nahrain_central_api.common.service.mapper.user;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.Mapper;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.ProfDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.Prof;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.repository.user.ProfRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfMapper implements Mapper<Prof, ProfDto> {
  private final ProfRepository profRepository;

  @Override
  public Prof mapTo(ProfDto profDto) {
    return profRepository
        .findById(profDto.getId())
        .orElseThrow(() -> new EntityNotFoundException("Prof not found with id: " + profDto.getId()));
  }

  @Override
  public List<Prof> mapToList(List<ProfDto> profDtos) {
    if (profDtos == null || profDtos.isEmpty()) {
      return Collections.emptyList();
    }

    Set<Integer> profIds = profDtos.stream().map(ProfDto::getId).collect(Collectors.toSet());

    Map<Integer, Prof> profMap =
        profRepository.findAllById(profIds).stream()
            .collect(Collectors.toMap(Prof::getId, Function.identity()));

    return profDtos.stream()
        .map(dto -> profMap.getOrDefault(dto.getId(), throwNotFoundException(dto.getId())))
        .toList();
  }

  private Prof throwNotFoundException(Integer id) {
    throw new EntityNotFoundException("Prof not found with id: " + id);
  }
}

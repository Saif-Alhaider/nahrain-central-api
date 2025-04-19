package io.github.saifalhaider.nahrain.nahrain_central_api.admin.curriculum.getCurriculums;


import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.curriculum.Curriculum;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.stage.StageType;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.repository.CurriculumRepository;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.service.mapper.CurriculumDtoMapper;
import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetCurriculumsService {

    private final CurriculumRepository curriculumRepository;
    private final CurriculumDtoMapper curriculumDtoMapper;

    @Transactional
    public CurriculumByYearResponseDto getAllCurriculaByYear() {
        List<Curriculum> allCurricula = curriculumRepository.findAll();

        return CurriculumByYearResponseDto.builder()
                .firstYear(getYearDataOrNull(allCurricula, StageType.FIRST))
                .secondYear(getYearDataOrNull(allCurricula, StageType.SECOND))
                .thirdYear(getYearDataOrNull(allCurricula, StageType.THIRD))
                .finalYear(getYearDataOrNull(allCurricula, StageType.FOURTH))
                .build();
    }

    @Nullable
    private CurriculumByYearResponseDto.CurriculumYearDto getYearDataOrNull(List<Curriculum> curricula, StageType stageType) {
        List<Curriculum> filtered = curricula.stream()
                .filter(c -> c.getStage().getStageType() == stageType)
                .toList();

        return CurriculumByYearResponseDto.CurriculumYearDto.builder()
                .size(filtered.size())
                .items(filtered.stream()
                        .map(curriculumDtoMapper::mapTo)
                        .toList())
                .build();
    }
}
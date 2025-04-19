package io.github.saifalhaider.nahrain.nahrain_central_api.common.service.mapper;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.CurriculumDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.Mapper;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.curriculum.Curriculum;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.service.mapper.user.ProfDtoMapper;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurriculumDtoMapper implements Mapper<CurriculumDto, Curriculum> {
    private final ProfDtoMapper profDtoMapper;

    @Override
    public CurriculumDto mapTo(Curriculum curriculum) {
        return CurriculumDto.builder()
                .id(curriculum.getId())
                .name(curriculum.getName())
                .stageType(curriculum.getStage().getStageType())
                .type(toDtoType(curriculum.getType()))
                .profs(profDtoMapper.mapToList(curriculum.getProfs()))
                .resources(curriculum.getResourcesList())
                .build();
    }

    @Override
    public List<CurriculumDto> mapToList(List<Curriculum> dtoList) {
        return dtoList.stream().map(this::mapTo).collect(Collectors.toList());
    }

    private CurriculumDto.Type toDtoType(Curriculum.Type curriculumType) {
        return switch (curriculumType) {
            case THEORETICAL -> CurriculumDto.Type.THEORETICAL;
            case LAB -> CurriculumDto.Type.LAB;
            case THEORETICAL_AND_LAB -> CurriculumDto.Type.THEORETICAL_AND_LAB;
        };
    }
}

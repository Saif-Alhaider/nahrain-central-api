package io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
public class ProfDto extends UserDto {
    private final List<CurriculumDto> curriculums;
}

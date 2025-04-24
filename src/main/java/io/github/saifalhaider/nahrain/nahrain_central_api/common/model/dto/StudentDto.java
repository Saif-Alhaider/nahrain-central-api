package io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.stage.StageType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class StudentDto extends UserDto {
    private final StageType stageType;
}

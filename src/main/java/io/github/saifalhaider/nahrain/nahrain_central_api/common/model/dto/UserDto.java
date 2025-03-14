package io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto;

import lombok.experimental.SuperBuilder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuperBuilder
public abstract class UserDto {
    private Integer id;
    private String fullName;
    private String email;
}

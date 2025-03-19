package io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public abstract class UserDto {
    private Integer id;
    private String fullName;
    private String email;
    private LocalDateTime date;
}

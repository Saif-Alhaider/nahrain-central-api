package io.github.saifalhaider.nahrain.nahrain_central_api.admin.getUsers;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UsersDto<T extends UserDto> {
    private List<T> users;
    private int page;
    private int numberOfRecords;
}

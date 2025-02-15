package io.github.saifalhaider.nahrain.nahrain_central_api.common.base;

import java.util.List;

public interface Mapper<ENTITY, DTO> {
    ENTITY toEntity(DTO dto);

    List<ENTITY> toEntityList(List<DTO> dtoList);
}

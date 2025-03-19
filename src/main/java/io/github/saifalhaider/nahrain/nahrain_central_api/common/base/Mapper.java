package io.github.saifalhaider.nahrain.nahrain_central_api.common.base;

import java.util.List;

public interface Mapper<ENTITY, DTO> {
  ENTITY mapToDomain(DTO dto);

  List<ENTITY> mapToDomainList(List<DTO> dtoList);
}

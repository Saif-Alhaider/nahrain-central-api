package io.github.saifalhaider.nahrain.nahrain_central_api.common.base;

import java.util.List;

public interface Mapper<DTO, ENTITY> {
  DTO mapTo(ENTITY dto);

  List<DTO> mapToList(List<ENTITY> dtoList);
}

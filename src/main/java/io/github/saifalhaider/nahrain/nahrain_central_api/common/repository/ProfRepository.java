package io.github.saifalhaider.nahrain.nahrain_central_api.common.repository;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.Prof;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfRepository extends UserRepository<Prof, Integer> {
}

package io.github.saifalhaider.nahrain.nahrain_central_api.common.repository.user;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.Prof;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfRepository extends UserRepository<Prof, Integer> {}

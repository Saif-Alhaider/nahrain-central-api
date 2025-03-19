package io.github.saifalhaider.nahrain.nahrain_central_api.common.repository;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.PendingUser;
import org.springframework.stereotype.Repository;

@Repository
public interface PendingUserRepository extends UserRepository<PendingUser, Integer> {}

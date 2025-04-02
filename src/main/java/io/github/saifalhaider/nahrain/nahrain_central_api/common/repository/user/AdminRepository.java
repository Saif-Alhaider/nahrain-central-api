package io.github.saifalhaider.nahrain.nahrain_central_api.common.repository.user;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.Admin;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends UserRepository<Admin, Integer> {}

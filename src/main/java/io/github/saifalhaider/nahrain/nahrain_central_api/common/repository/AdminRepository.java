package io.github.saifalhaider.nahrain.nahrain_central_api.common.repository;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.Admin;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends UserRepository<Admin, Integer> {
}
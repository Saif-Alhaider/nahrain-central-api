package io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@Entity
@SuperBuilder
@Table(name = "_prof")
@PrimaryKeyJoinColumn(name = "id")
public class Prof extends User {
}

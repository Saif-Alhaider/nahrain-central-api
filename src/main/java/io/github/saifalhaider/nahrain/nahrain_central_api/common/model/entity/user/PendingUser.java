package io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@Entity
@SuperBuilder
@Table(name = "pending_user")
@PrimaryKeyJoinColumn(name = "id")
public class PendingUser extends User {}

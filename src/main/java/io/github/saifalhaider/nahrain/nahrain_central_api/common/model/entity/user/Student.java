package io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.stage.Stage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@SuperBuilder
@Table(name = "_student")
@PrimaryKeyJoinColumn(name = "id")
public class Student extends User {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "stage_id")
  private Stage stage;
}

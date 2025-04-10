package io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.stage;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.Student;
import jakarta.persistence.*;
import java.util.List;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "_stage")
public class Stage {
  @Id @GeneratedValue private Integer id;

  @OneToMany(mappedBy = "stage", cascade = CascadeType.ALL)
  private List<Student> students;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, unique = true)
  private StageType stageType;
}

package io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.curriculum;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.stage.Stage;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.Prof;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "_curriculum")
@Builder(builderMethodName = "customBuilder")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Curriculum {
  @Id @GeneratedValue private Long id;

  Type type;
  String name;
  String resources;

  @ManyToOne
  @JoinColumn(name = "stage_id")
  Stage stage;

  public List<String> getResourcesList() {
    return resources != null ? Arrays.asList(resources.split(",")) : null;
  }

  public void setResourcesList(List<String> items) {
    this.resources = items != null ? String.join(",", items) : null;
  }

  public static class CurriculumBuilder {
    private List<String> resourcesList;

    public CurriculumBuilder resourcesList(List<String> list) {
      this.resources = list != null ? String.join(",", list) : null;
      return this;
    }
  }

  public enum Type {
    THEORETICAL,
    LAB,
    THEORETICAL_AND_LAB,
  }

  @ManyToMany
  @JoinTable(
      name = "curriculum_profs",
      joinColumns = @JoinColumn(name = "curriculum_id"),
      inverseJoinColumns = @JoinColumn(name = "prof_id"))
  private List<Prof> profs;
}

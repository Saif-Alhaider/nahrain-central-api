package io.github.saifalhaider.nahrain.nahrain_central_api.admin.curriculum.editCurriculum;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.ApiResponseDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.CurriculumDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/admin")
@RequiredArgsConstructor
public class EditCurriculumController {
  private final EditCurriculumService editCurriculumService;

  @PutMapping("/curricula")
  public ResponseEntity<ApiResponseDto<CurriculumDto>> updateCurriculum(
      @RequestBody EditCurriculumRequest request) {

    CurriculumDto updatedCurriculum = editCurriculumService.updateCurriculum(request);
    return ResponseEntity.ok(
        ApiResponseDto.response(
            ApiResponseDto.StatusInfo.builder()
                .code(HttpStatus.OK.value())
                .message("Curriculum updated successfully")
                .build(),
            updatedCurriculum));
  }
}

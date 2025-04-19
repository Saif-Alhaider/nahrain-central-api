package io.github.saifalhaider.nahrain.nahrain_central_api.admin.curriculum.getCurriculum;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.ApiResponseDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.CurriculumDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/admin")
@RequiredArgsConstructor
public class GetCurriculumController {
  private final GetCurriculumService getCurriculumService;

  @GetMapping("/curricula/{id}")
  public ResponseEntity<ApiResponseDto<CurriculumDto>> getCurriculum(@PathVariable Long id) {
    CurriculumDto response = getCurriculumService.getCurriculum(id);
    return ResponseEntity.ok(
        ApiResponseDto.response(
            ApiResponseDto.StatusInfo.builder()
                .code(HttpStatus.OK.value())
                .message("Curriculum updated successfully")
                .build(),
            response));
  }
}

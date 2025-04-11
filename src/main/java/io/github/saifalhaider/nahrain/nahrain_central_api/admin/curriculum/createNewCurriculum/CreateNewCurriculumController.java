package io.github.saifalhaider.nahrain.nahrain_central_api.admin.curriculum.createNewCurriculum;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.CurriculumDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/admin")
@RequiredArgsConstructor
public class CreateNewCurriculumController {
  private final CreateNewCurriculumService createNewCurriculumService;

  @PostMapping("/curricula")
  public ApiResponseDto<CurriculumDto> createCurriculum(@RequestBody CurriculumRequest request) {

    CurriculumDto response = createNewCurriculumService.createCurriculum(request);

    return ApiResponseDto.response(
        ApiResponseDto.StatusInfo.builder()
            .code(HttpStatus.CREATED.value())
            .message("Curriculum created successfully")
            .build(),
        response);
  }
}

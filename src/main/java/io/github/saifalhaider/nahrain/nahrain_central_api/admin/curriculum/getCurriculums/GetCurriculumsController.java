package io.github.saifalhaider.nahrain.nahrain_central_api.admin.curriculum.getCurriculums;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/admin")
@RequiredArgsConstructor
public class GetCurriculumsController {
  private final GetCurriculumsService getCurriculumsService;

  @GetMapping("/curricula")
  public ApiResponseDto<CurriculumByYearResponseDto> getAllCurricula() {
    CurriculumByYearResponseDto response = getCurriculumsService.getAllCurriculaByYear();
    return buildSuccessResponse(response);
  }

  private ApiResponseDto<CurriculumByYearResponseDto> buildSuccessResponse(
      CurriculumByYearResponseDto data) {
    return ApiResponseDto.response(
        ApiResponseDto.StatusInfo.builder()
            .code(HttpStatus.OK.value())
            .message("Curricula retrieved successfully")
            .build(),
        data);
  }
}

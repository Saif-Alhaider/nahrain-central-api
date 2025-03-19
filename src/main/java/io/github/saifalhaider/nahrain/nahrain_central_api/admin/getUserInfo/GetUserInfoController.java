package io.github.saifalhaider.nahrain.nahrain_central_api.admin.getUserInfo;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.ApiResponseDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/admin")
@RequiredArgsConstructor
public class GetUserInfoController {
  private final GetUserInfoService getUserInfoService;

  @GetMapping("/users/{id}")
  public ResponseEntity<ApiResponseDto<? extends UserDto>> getUseInfo(@PathVariable Integer id) {
    return getUserInfoService.getUserInfo(id);
  }
}

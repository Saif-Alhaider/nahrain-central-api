package io.github.saifalhaider.nahrain.nahrain_central_api.admin.createNewUser;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.ApiResponseDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.UserDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/admin")
@RequiredArgsConstructor
public class CreateNewUserController {
    private final CreateNewUserService createNewUserService;

    @PostMapping("/users")
    public ResponseEntity<ApiResponseDto<UserDto>> createNewUser(@RequestBody NewUserDto newUserDto) {
        ApiResponseDto<UserDto> payload = createNewUserService.createNewUser(newUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(payload);
    }
}

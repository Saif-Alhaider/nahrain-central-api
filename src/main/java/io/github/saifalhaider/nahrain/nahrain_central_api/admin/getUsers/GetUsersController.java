package io.github.saifalhaider.nahrain.nahrain_central_api.admin.getUsers;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.ApiResponseDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.AdminDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.PendingUserDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.ProfDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.StudentDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.Admin;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.PendingUser;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.Prof;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/admin")
@RequiredArgsConstructor
public class GetUsersController {
    private final GetUsersService getUsersService;


    @GetMapping("/students")
    public ApiResponseDto<UsersDto<StudentDto>> getStudents(
            @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @RequestParam(defaultValue = "1", required = false) int pageSize) {
        return getUsersService.getStudents(pageNumber, pageSize);
    }

    @GetMapping("/admins")
    public ApiResponseDto<UsersDto<AdminDto>> getAdmins(
            @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @RequestParam(defaultValue = "1", required = false) int pageSize) {
        return getUsersService.getAdmins(pageNumber, pageSize);
    }

    @GetMapping("/profs")
    public ApiResponseDto<UsersDto<ProfDto>> getProfs(
            @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @RequestParam(defaultValue = "1", required = false) int pageSize) {
        return getUsersService.getProfs(pageNumber, pageSize);
    }

    @GetMapping("/pending-users")
    public ApiResponseDto<UsersDto<PendingUserDto>> getPendingUsers(
            @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @RequestParam(defaultValue = "1", required = false) int pageSize) {
        return getUsersService.getPendingUsers(pageNumber, pageSize);
    }
}

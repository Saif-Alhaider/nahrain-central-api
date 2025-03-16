package io.github.saifalhaider.nahrain.nahrain_central_api.admin.getUsers;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.ApiResponseDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.*;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.Admin;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.PendingUser;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.Prof;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.Student;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.User;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetUsersService {
    final private StudentRepository studentRepository;
    private final AdminRepository adminRepository;
    private final ProfRepository profRepository;
    private final PendingUserRepository pendingUserRepository;


    public ApiResponseDto<UsersDto<StudentDto>> getStudents(int pageNumber, int pageSize) {

        Function<Page<Student>, UsersDto<StudentDto>> studentMapper = page -> {
            List<StudentDto> students = page
                    .getContent()
                    .stream()
                    .map(student -> StudentDto.builder()
                            .id(student.getId())
                            .fullName(student.getFullName())
                            .email(student.getEmail())
                            .date(student.getCreatedDate())
                            .build())
                    .collect(Collectors.toList());

            return UsersDto.<StudentDto>builder()
                    .users(students)
                    .totalPages(page.getTotalPages())
                    .totalNumberOfUsers(page.getTotalElements())
                    .build();
        };

        return getPaginatedData(pageNumber, pageSize, studentRepository, studentMapper, "Students retrieved successfully");
    }

    public ApiResponseDto<UsersDto<AdminDto>> getAdmins(int pageNumber, int pageSize) {

        Function<Page<Admin>, UsersDto<AdminDto>> adminMapper = page -> {
            List<AdminDto> admins = page
                    .getContent()
                    .stream()
                    .map(admin -> AdminDto.builder()
                            .id(admin.getId())
                            .fullName(admin.getFullName())
                            .email(admin.getEmail())
                            .date(admin.getCreatedDate())
                            .build())
                    .collect(Collectors.toList());

            return UsersDto.<AdminDto>builder()
                    .users(admins)
                    .totalPages(page.getTotalPages())
                    .totalNumberOfUsers(page.getTotalElements())
                    .build();
        };

        return getPaginatedData(pageNumber, pageSize, adminRepository, adminMapper, "Admins retrieved successfully");
    }

    public ApiResponseDto<UsersDto<ProfDto>> getProfs(int pageNumber, int pageSize) {

        Function<Page<Prof>, UsersDto<ProfDto>> profMapper = page -> {
            List<ProfDto> profs = page
                    .getContent()
                    .stream()
                    .map(prof -> ProfDto.builder()
                            .id(prof.getId())
                            .fullName(prof.getFullName())
                            .email(prof.getEmail())
                            .date(prof.getCreatedDate())
                            .build())
                    .collect(Collectors.toList());

            return UsersDto.<ProfDto>builder()
                    .users(profs)
                    .totalPages(page.getTotalPages())
                    .totalNumberOfUsers(page.getTotalElements())
                    .build();
        };

        return getPaginatedData(pageNumber, pageSize, profRepository, profMapper, "Profs retrieved successfully");
    }

    public ApiResponseDto<UsersDto<PendingUserDto>> getPendingUsers(int pageNumber, int pageSize) {

        Function<Page<PendingUser>, UsersDto<PendingUserDto>> pendingUserMapper = page -> {
            List<PendingUserDto> pendingUsers = page
                    .getContent()
                    .stream()
                    .map(pendingUser -> PendingUserDto.builder()
                            .id(pendingUser.getId())
                            .fullName(pendingUser.getFullName())
                            .email(pendingUser.getEmail())
                            .date(pendingUser.getCreatedDate())
                            .build())
                    .collect(Collectors.toList());

            return UsersDto.<PendingUserDto>builder()
                    .users(pendingUsers)
                    .totalPages(page.getTotalPages())
                    .totalNumberOfUsers(page.getTotalElements())
                    .build();
        };


        return getPaginatedData(pageNumber, pageSize, pendingUserRepository, pendingUserMapper,"Pending Users retrieved successfully");
    }

    public <ENT extends User, DTO extends UserDto> ApiResponseDto<UsersDto<DTO>> getPaginatedData(
            int pageNumber,
            int pageSize,
            UserRepository<ENT, Integer> userRepository,
            Function<Page<ENT>, UsersDto<DTO>> mapper,
            String successMessage) {

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

        Page<ENT> page = userRepository.findAll(pageRequest);

        UsersDto<DTO> data = mapper.apply(page);

        return ApiResponseDto.response(
                ApiResponseDto.StatusInfo.builder()
                        .code(200)
                        .message(successMessage)
                        .build(),
                data
        );
    }
}

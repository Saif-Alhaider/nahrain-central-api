package io.github.saifalhaider.nahrain.nahrain_central_api.admin.SetPendingUserRole;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.ApiResponseDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.AdminDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.ProfDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.StudentDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.UserDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.Admin;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.PendingUser;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.Prof;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.Student;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.User;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.UserFactory;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.repository.AdminRepository;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.repository.PendingUserRepository;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.repository.ProfRepository;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SetPendingUserRoleService {
    private final ProfRepository profRepository;
    private final PendingUserRepository pendingUserRepository;
    private final AdminRepository adminRepository;
    private final StudentRepository studentRepository;


    @Transactional
    public ApiResponseDto<UserDto> updatePendingUserRole(Integer id, User.Role role) {
        PendingUser user = pendingUserRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User with id" + id + " not found"));

        user.setRole(role);



        User roleUser = UserFactory.createUser(
                user.getFullName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole(),
                user.isMfaEnabled(),
                user.getTotpSecret());


        if (roleUser instanceof Admin) {
            adminRepository.save((Admin) roleUser);
        } else if (roleUser instanceof Prof) {
            profRepository.save((Prof) roleUser);
        } else if (roleUser instanceof Student) {
            studentRepository.save((Student) roleUser);
        } else {
            throw new IllegalArgumentException("Unknown user role: " + roleUser.getClass().getSimpleName());
        }

        pendingUserRepository.deleteById(id);

        UserDto response;


        if (roleUser instanceof Admin) {
            Admin admin = adminRepository.save((Admin) roleUser);
            response = AdminDto.builder()
                    .id(admin.getId())
                    .date(admin.getCreatedDate())
                    .fullName(admin.getFullName())
                    .email(admin.getEmail())
                    .build();
        } else if (roleUser instanceof Prof) {
            Prof prof = profRepository.save((Prof) roleUser);
            response = ProfDto.builder()
                    .id(prof.getId())
                    .date(prof.getCreatedDate())
                    .fullName(prof.getFullName())
                    .email(prof.getEmail())
                    .build();
        } else {
            Student student = studentRepository.save((Student) roleUser);
            response = StudentDto.builder()
                    .id(student.getId())
                    .date(student.getCreatedDate())
                    .fullName(student.getFullName())
                    .email(student.getEmail())
                    .build();
        }

        return ApiResponseDto.response(
                ApiResponseDto.StatusInfo.builder()
                        .code(200)
                        .message("Pending user role updated successfully")
                        .build(),
                response
        );
    }
}

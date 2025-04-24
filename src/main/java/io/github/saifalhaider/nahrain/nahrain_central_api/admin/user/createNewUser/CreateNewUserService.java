package io.github.saifalhaider.nahrain.nahrain_central_api.admin.user.createNewUser;

import io.github.saifalhaider.nahrain.nahrain_central_api.auth.model.responseCode.AuthResponseCode;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.exception.EmailNotValid;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.exception.UserAlreadyExists;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.validation.email.EmailValidator;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.ApiResponseDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.AdminDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.ProfDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.StudentDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.UserDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.stage.Stage;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.Admin;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.Prof;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.Student;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.User;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.UserFactory;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.repository.stage.StageRepository;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.repository.user.AdminRepository;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.repository.user.ProfRepository;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.repository.user.StudentRepository;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.repository.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateNewUserService {

  private final UserRepository<User, Integer> userRepository;
  private final AdminRepository adminRepository;
  private final ProfRepository profRepository;
  private final StudentRepository studentRepository;
  private final EmailValidator emailValidator;
  private final StageRepository stageRepository;

  public ApiResponseDto<UserDto> createNewUser(NewUserDto newUserDto) {
    if (!emailValidator.isValid(newUserDto.getEmail())) {
      throw new EmailNotValid("The email must belong to nahrainuniv.edu.iq domain");
    }
    val userEmail = emailValidator.getCompletedEmail(newUserDto.getEmail());
    if (isUserExists(userEmail)) {
      throw new UserAlreadyExists(
          "The user with email " + newUserDto.getEmail() + " already exists");
    }

    User user =
        UserFactory.createUser(
            newUserDto.getFullName(), userEmail, null, newUserDto.getRole(), null, null);

    UserDto response;

    if (user instanceof Admin) {
      Admin admin = adminRepository.save((Admin) user);
      response =
          AdminDto.builder()
              .id(admin.getId())
              .date(admin.getCreatedDate())
              .fullName(admin.getFullName())
              .email(admin.getEmail())
              .build();
    } else if (user instanceof Prof) {
      Prof prof = profRepository.save((Prof) user);
      response =
          ProfDto.builder()
              .id(prof.getId())
              .date(prof.getCreatedDate())
              .fullName(prof.getFullName())
              .email(prof.getEmail())
              .build();
    } else if (user instanceof Student student) {

      Stage stage =
          stageRepository
              .findByStageType(newUserDto.getStageType())
              .orElseThrow(() -> new EntityNotFoundException("Stage not found"));

      student.setStage(stage);

      Student savedStudent = studentRepository.save(student);

      response =
          StudentDto.builder()
              .id(savedStudent.getId())
              .date(savedStudent.getCreatedDate())
              .fullName(savedStudent.getFullName())
              .email(savedStudent.getEmail())
              .stageType(savedStudent.getStage().getStageType())
              .build();
    } else {
      throw new RuntimeException("Unknown Role");
    }

    return ApiResponseDto.response(
        ApiResponseDto.StatusInfo.builder()
            .code(AuthResponseCode.NEW_USER_CREATED.getCode())
            .message(AuthResponseCode.NEW_USER_CREATED.getMessage())
            .build(),
        response);
  }

  private boolean isUserExists(String email) {
    return userRepository.findByEmail(email).isPresent();
  }
}

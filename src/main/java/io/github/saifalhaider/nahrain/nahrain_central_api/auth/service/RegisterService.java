package io.github.saifalhaider.nahrain.nahrain_central_api.auth.service;

import io.github.saifalhaider.nahrain.nahrain_central_api.auth.model.dto.AuthenticationResponseDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.model.dto.RegisterRequestDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.model.responseCode.AuthResponseCode;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.exception.EmailNotValid;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.exception.UserAlreadyExists;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.validation.email.EmailValidator;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.ApiResponseDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.BaseResponseCode;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.Mapper;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.User;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterService {
    private final UserRepository userRepository;
    private final EmailValidator emailValidator;
    private final Mapper<User, RegisterRequestDto> userMapper;
    private final Mapper<ApiResponseDto.StatusInfo, BaseResponseCode> baseResponseCodeToInfoMapper;
    private final AuthSessionIssuerService authSessionIssuerService;

    public ResponseEntity<ApiResponseDto<AuthenticationResponseDto>> register(RegisterRequestDto request) throws UserAlreadyExists, EmailNotValid {
        validateRegisterRequest(request);

        User user = userMapper.toEntity(request);

        userRepository.save(user);

        val authTokens = authSessionIssuerService.generateNewAuthToken(user);

        AuthenticationResponseDto payload = AuthenticationResponseDto.builder().token(authTokens.getToken()).build();
        ApiResponseDto.StatusInfo statusInfo = baseResponseCodeToInfoMapper.toEntity(AuthResponseCode.REGISTER_SUCCESSFUL);

        return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.SET_COOKIE, authTokens.getRefreshToken().toString())
                .body(ApiResponseDto.response(statusInfo, payload));
    }

    private void validateRegisterRequest(RegisterRequestDto request) throws UserAlreadyExists, EmailNotValid {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExists("The user with email " + request.getEmail() + " already exists");
        } else if (!emailValidator.isValid(request.getEmail())) {
            throw new EmailNotValid("The email must belong to nahrainuniv.edu.iq domain");
        }
    }
}

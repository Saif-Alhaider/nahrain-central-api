package io.github.saifalhaider.nahrain.nahrain_central_api.auth.service;

import io.github.saifalhaider.nahrain.nahrain_central_api.auth.model.dto.AuthenticationResponseDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.model.dto.RegisterRequestDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.model.responseCode.AuthResponseCode;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.exception.EmailNotValid;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.exception.UserAlreadyExists;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.handler.JwtAccessTokenHandler;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.validation.email.EmailValidator;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.ApiResponseDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.BaseResponseCode;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.Mapper;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.User;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.repository.user.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterService {
    private final UserRepository<User, Integer> userRepository;
    private final EmailValidator emailValidator;
    private final Mapper<User, RegisterRequestDto> userMapper;
    private final Mapper<ApiResponseDto.StatusInfo, BaseResponseCode> baseResponseCodeToInfoMapper;
    private final JwtAccessTokenHandler jwtAccessTokenHandler;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<ApiResponseDto<AuthenticationResponseDto>> register(RegisterRequestDto request)
            throws UserAlreadyExists, EmailNotValid {
        request.setEmail(emailValidator.getCompletedEmail(request.getEmail()));

        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());

        if (existingUser.isPresent()) {
            return handleExistingUser(existingUser.get(), request);
        }

        validateEmail(request.getEmail());

        User user = saveNewUser(request);
        return generateSuccessResponse(user);
    }

    private ResponseEntity<ApiResponseDto<AuthenticationResponseDto>> handleExistingUser(User user, RegisterRequestDto request)
            throws UserAlreadyExists {

        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            throw new UserAlreadyExists("The user with email " + request.getEmail() + " already exists");
        }

        userRepository.updateUserPassword(request.getEmail(), passwordEncoder.encode(request.getPassword()));
        return generateSuccessResponse(user);
    }

    private void validateEmail(String email) throws EmailNotValid {
        if (!emailValidator.isValid(email)) {
            throw new EmailNotValid("The email must belong to nahrainuniv.edu.iq domain");
        }
    }

    private User saveNewUser(RegisterRequestDto request) {
        User user = userMapper.mapToDomain(request);
        userRepository.save(user);
        return user;
    }

    private ResponseEntity<ApiResponseDto<AuthenticationResponseDto>> generateSuccessResponse(User user) {
        AuthenticationResponseDto authResponse = generateAuthTokens(user);
        ApiResponseDto.StatusInfo statusInfo = baseResponseCodeToInfoMapper.mapToDomain(AuthResponseCode.REGISTER_SUCCESSFUL);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDto.response(statusInfo, authResponse));
    }

    private AuthenticationResponseDto generateAuthTokens(User user) {
        val accessToken = jwtAccessTokenHandler.generateAccessToken(user);
        val refreshToken = jwtAccessTokenHandler.generateRefreshToken(user);

        return AuthenticationResponseDto.builder()
                .token(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}


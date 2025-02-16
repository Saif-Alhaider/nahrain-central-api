package io.github.saifalhaider.nahrain.nahrain_central_api.service.auth;

import io.github.saifalhaider.nahrain.nahrain_central_api.auth.model.entity.RefreshToken;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.RegisterService;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.exception.EmailNotValid;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.exception.UserAlreadyExists;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.jwt.JwtService;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.jwt.RefreshTokenService;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.validation.email.EmailValidator;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.ApiResponseDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.model.dto.RegisterRequestDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.BaseResponseCode;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.User;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.repository.UserRepository;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RegisterServiceTest {

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private EmailValidator emailValidator;

    @MockitoBean
    private Mapper<User, RegisterRequestDto> userMapper;

    @MockitoBean
    private RefreshTokenService refreshTokenService;

    @Autowired
    private RegisterService authenticationService;

    @MockitoBean
    private Mapper<ApiResponseDto.StatusInfo, BaseResponseCode> baseResponseCodeToInfoMapper; // ✅ Mocked mapper

    @BeforeEach
    public void setUp() {
        authenticationService = new RegisterService(userRepository,
                jwtService,
                refreshTokenService,
                emailValidator,
                userMapper,
                baseResponseCodeToInfoMapper
        );
    }


    @Test
    public void should_throw_user_already_exists() {
        RegisterRequestDto request = new RegisterRequestDto();
        request.setEmail("test@nahrainuniv.edu.iq");
        request.setPassword("test1234");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User()));

        assertThrows(UserAlreadyExists.class, () -> authenticationService.register(request));
    }

    @Test
    public void should_throw_EmailNotValid_error_when_email_does_not_contain_nahrain_pattern() {
        RegisterRequestDto request = new RegisterRequestDto();
        request.setEmail("test@nahrainuniv.edu.iq");
        request.setPassword("test1234");
        when(emailValidator.isValid(request.getEmail())).thenReturn(false);

        assertThrows(EmailNotValid.class, () -> authenticationService.register(request));
    }

    @Test
    public void should_register_new_user_when_email_is_valid() throws UserAlreadyExists, EmailNotValid {
        // Arrange
        RegisterRequestDto request = new RegisterRequestDto();
        request.setEmail("test@nahrainuniv.edu.iq");
        request.setPassword("test1234");

        // Mock User
        User mockUser = User.builder().id(1).build();

        // Mock a valid refresh token
        RefreshToken mockToken = RefreshToken.builder().token("mock-refresh-token").build();

        // Mock external dependencies
        when(emailValidator.isValid(request.getEmail())).thenReturn(true);
        when(userMapper.toEntity(request)).thenReturn(mockUser);
        when(userRepository.save(mockUser)).thenReturn(mockUser);
        when(refreshTokenService.createRefreshToken(mockUser.getId())).thenReturn(mockToken);

        //Assert
        assertDoesNotThrow(() -> authenticationService.register(request));
    }


}
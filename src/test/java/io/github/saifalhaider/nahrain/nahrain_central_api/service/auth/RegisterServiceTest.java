package io.github.saifalhaider.nahrain.nahrain_central_api.service.auth;

import io.github.saifalhaider.nahrain.nahrain_central_api.model.dto.ApiResponseDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.model.dto.auth.RegisterRequestDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.model.dto.responseCode.BaseResponseCode;
import io.github.saifalhaider.nahrain.nahrain_central_api.model.entity.User;
import io.github.saifalhaider.nahrain.nahrain_central_api.repository.UserRepository;
import io.github.saifalhaider.nahrain.nahrain_central_api.service.auth.exception.EmailNotValid;
import io.github.saifalhaider.nahrain.nahrain_central_api.service.auth.exception.UserAlreadyExists;
import io.github.saifalhaider.nahrain.nahrain_central_api.service.mapper.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Autowired
    private RegisterService authenticationService;

    @MockitoBean
    private Mapper<ApiResponseDto.StatusInfo, BaseResponseCode> baseResponseCodeToInfoMapper; // ✅ Mocked mapper

    @BeforeEach
    public void setUp() {
        authenticationService = new RegisterService(userRepository,
                jwtService,
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
    public void should_register_new_user_when_email_is_valid() {
        RegisterRequestDto request = new RegisterRequestDto();
        request.setEmail("test@nahrainuniv.edu.iq");
        request.setPassword("test1234");

        when(emailValidator.isValid(request.getEmail())).thenReturn(true);

        assertDoesNotThrow(() -> authenticationService.register(request));
    }
}
package io.github.saifalhaider.nahrain.nahrain_central_api.service.auth;

import io.github.saifalhaider.nahrain.nahrain_central_api.dto.authDto.AuthenticationResponseDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.dto.authDto.LoginRequestDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.entity.User;
import io.github.saifalhaider.nahrain.nahrain_central_api.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class LoginServiceTest {

    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private UserRepository userRepository;

    private LoginService loginService;

    @BeforeEach
    public void setUp() {
        loginService = new LoginService(jwtService, authenticationManager, userRepository);
    }

    @Test
    public void should_return_responseDto_when_user_exists() {
        LoginRequestDto loginRequestDto = LoginRequestDto.builder().email("example@nahrainuniv.edu.iq").password("password1234").build();
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User()));
        when(jwtService.generateToken(any())).thenReturn("jwtToken");

        AuthenticationResponseDto result = loginService.login(loginRequestDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("jwtToken", result.getToken());
    }

    @Test
    public void login_WhenUserDoesNotExist_ThrowsUsernameNotFoundException() {
        LoginRequestDto loginRequestDto = LoginRequestDto.builder().email("example@nahrainuniv.edu.iq").password("password1234").build();
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(UsernameNotFoundException.class, () -> loginService.login(loginRequestDto));
    }
}
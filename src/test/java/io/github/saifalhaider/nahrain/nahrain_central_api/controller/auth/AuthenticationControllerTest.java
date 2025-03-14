package io.github.saifalhaider.nahrain.nahrain_central_api.controller.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.controller.AuthenticationController;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.model.dto.LoginRequestDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.model.dto.RegisterRequestDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.model.responseCode.AuthResponseCode;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.LoginService;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.RefreshTokenService;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.RegisterService;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.exception.UserAlreadyExists;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.jwt.JwtHelper;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.validation.jwt.JwtValidator;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.ApiResponseDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.BaseResponseCode;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.Mapper;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RegisterService registerService;

    @MockitoBean
    private LoginService loginService;
    @MockitoBean
    private JwtHelper jwtHelper;
    @MockitoBean
    private JwtValidator jwtValidator;

    @MockitoBean
    private Mapper<ApiResponseDto.StatusInfo, BaseResponseCode> baseResponseCodeToInfoMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private RefreshTokenService refreshTokenService;

//    @Test
//    public void shouldReturnMockedControllerResponse() throws Exception {
//        val request = new RegisterRequestDto("test@nahrainuniv.edu.iq", "password123");
//
//        ApiResponseDto.StatusInfo statusInfo = ApiResponseDto.StatusInfo.builder().code(101).message("User registered").build();
//        AuthenticationResponseDto fakeUser = new AuthenticationResponseDto("token");
//        ApiResponseDto<AuthenticationResponseDto> mockApiResponse = new ApiResponseDto<>(statusInfo, fakeUser);
//
//        ResponseEntity<ApiResponseDto<AuthenticationResponseDto>> mockResponse = ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body(mockApiResponse);
//
//        when(registerService.register(any(RegisterRequestDto.class))).thenReturn(mockResponse);
//
//        mockMvc.perform(post("/api/v1/auth/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.info.code").value(101))
//                .andExpect(jsonPath("$.info.message").value("User registered"))
//                .andExpect(jsonPath("$.payload.token").value("token"));
//    }

    @Test
    public void should_Return_UserNotFoundException_When_LoginFails() throws Exception {
        val request = new LoginRequestDto("foo@nahrainuniv.edu.iq", "invalidPassword");

        when(loginService.login(any(LoginRequestDto.class))).thenThrow(new UsernameNotFoundException("User not found"));

        val authResponseCode = AuthResponseCode.USER_NOT_FOUND;

        val statusInfo = ApiResponseDto.StatusInfo.builder()
                .code(authResponseCode.getCode())
                .message(authResponseCode.getMessage())
                .build();

        when(baseResponseCodeToInfoMapper.toEntity(authResponseCode)).thenReturn(statusInfo);

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.info.message").value(authResponseCode.getMessage()));
    }

    @Test
    public void should_Return_UserAlreadyExistsException_When_RegistrationFails() throws Exception {
        val request = new RegisterRequestDto("test@nahrainuniv.edu.iq", "password123");

        when(registerService.register(any(RegisterRequestDto.class))).thenThrow(new UserAlreadyExists("User already exists"));

        val authResponseCode = AuthResponseCode.USER_ALREADY_EXISTS;

        val statusInfo = ApiResponseDto.StatusInfo.builder()
                .code(authResponseCode.getCode())
                .message(authResponseCode.getMessage())
                .build();

        when(baseResponseCodeToInfoMapper.toEntity(authResponseCode)).thenReturn(statusInfo);

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.info.message").value(authResponseCode.getMessage()));
    }

//    @Test
//    public void should_Return_InvalidTokenException_When_TokenIsInvalid() throws Exception {
//        when(refreshTokenService.refreshToken(any(HttpServletRequest.class))).thenThrow(new InvalidToken("token", "Invalid token"));
//
//        val authResponseCode = AuthResponseCode.INVALID_TOKEN;
//
//        val statusInfo = ApiResponseDto.StatusInfo.builder()
//                .code(authResponseCode.getCode())
//                .message(authResponseCode.getMessage())
//                .build();
//
//        when(baseResponseCodeToInfoMapper.toEntity(authResponseCode)).thenReturn(statusInfo);
//
//        mockMvc.perform(post("/api/v1/auth/refreshtoken"))
//                .andExpect(status().isUnauthorized())
//                .andExpect(jsonPath("$.info.message").value(authResponseCode.getMessage()));
//    }

}

package io.github.saifalhaider.nahrain.nahrain_central_api.controller.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.saifalhaider.nahrain.nahrain_central_api.model.dto.ApiResponseDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.model.dto.auth.AuthenticationResponseDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.model.dto.auth.RegisterRequestDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.model.dto.responseCode.BaseResponseCode;
import io.github.saifalhaider.nahrain.nahrain_central_api.service.auth.JwtService;
import io.github.saifalhaider.nahrain.nahrain_central_api.service.auth.LoginService;
import io.github.saifalhaider.nahrain.nahrain_central_api.service.auth.RegisterService;
import io.github.saifalhaider.nahrain.nahrain_central_api.service.mapper.Mapper;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    private JwtService jwtService;

    @MockitoBean
    private Mapper<ApiResponseDto.StatusInfo, BaseResponseCode> baseResponseCodeToInfoMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldReturnMockedControllerResponse() throws Exception {
        val request = new RegisterRequestDto("test@nahrainuniv.edu.iq", "password123");

        ApiResponseDto.StatusInfo statusInfo = ApiResponseDto.StatusInfo.builder().code(101).message("User registered").build();
        AuthenticationResponseDto fakeUser = new AuthenticationResponseDto("token");
        ApiResponseDto<AuthenticationResponseDto> mockApiResponse = new ApiResponseDto<>(statusInfo, fakeUser);

        ResponseEntity<ApiResponseDto<AuthenticationResponseDto>> mockResponse = ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mockApiResponse);

        when(registerService.register(any(RegisterRequestDto.class))).thenReturn(mockResponse);

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.info.code").value(101))
                .andExpect(jsonPath("$.info.message").value("User registered"))
                .andExpect(jsonPath("$.payload.token").value("token"));
    }
}

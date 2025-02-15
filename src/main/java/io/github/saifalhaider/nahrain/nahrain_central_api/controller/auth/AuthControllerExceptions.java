package io.github.saifalhaider.nahrain.nahrain_central_api.controller.auth;

import io.github.saifalhaider.nahrain.nahrain_central_api.model.dto.ApiResponseDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.model.dto.responseCode.AuthResponseCode;
import io.github.saifalhaider.nahrain.nahrain_central_api.model.dto.responseCode.BaseResponseCode;
import io.github.saifalhaider.nahrain.nahrain_central_api.service.auth.exception.EmailNotValid;
import io.github.saifalhaider.nahrain.nahrain_central_api.service.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class AuthControllerExceptions {
    private final Mapper<ApiResponseDto.StatusInfo, BaseResponseCode> baseResponseCodeToInfoMapper;

    @ExceptionHandler(EmailNotValid.class)
    public ResponseEntity<ApiResponseDto<Map<String, Object>>> handleEmailNotValidException(EmailNotValid err) {

        Map<String, Object> errorResponse = Map.of(
                "timestamp", LocalDateTime.now(),
                "status", HttpStatus.BAD_REQUEST.value(),
                "error", "Bad Request",
                "message", err.getMessage()
        );

        val payload = ApiResponseDto.response(baseResponseCodeToInfoMapper.toEntity(AuthResponseCode.INVALID_EMAIL),errorResponse);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(payload);
    }
}

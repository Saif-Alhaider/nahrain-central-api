package io.github.saifalhaider.nahrain.nahrain_central_api.auth.controller;

import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.exception.EmailNotValid;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.exception.InvalidToken;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.ApiResponseDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.model.responseCode.AuthResponseCode;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.BaseResponseCode;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.Mapper;
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

    @ExceptionHandler({EmailNotValid.class, InvalidToken.class})
    public ResponseEntity<ApiResponseDto<Map<String, Object>>> handleAuthExceptions(RuntimeException err) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        AuthResponseCode responseCode;

        if (err instanceof EmailNotValid) {
            responseCode = AuthResponseCode.INVALID_EMAIL;
        } else if (err instanceof InvalidToken) {
            responseCode = AuthResponseCode.INVALID_TOKEN;
        }else {
            responseCode = AuthResponseCode.TOKEN_EXPIRED;
        }

        Map<String, Object> errorResponse = Map.of(
                "timestamp", LocalDateTime.now(),
                "status", status.value(),
                "error", status.getReasonPhrase(),
                "message", err.getMessage()
        );

        val payload = ApiResponseDto.response(baseResponseCodeToInfoMapper.toEntity(responseCode), errorResponse);

        return ResponseEntity.status(status).body(payload);
    }
}


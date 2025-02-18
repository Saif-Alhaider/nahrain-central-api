package io.github.saifalhaider.nahrain.nahrain_central_api.auth.controller;

import io.github.saifalhaider.nahrain.nahrain_central_api.auth.model.responseCode.AuthResponseCode;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.exception.EmailNotValid;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.exception.InvalidToken;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.exception.UserAlreadyExists;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.ApiResponseDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.BaseResponseCode;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class AuthControllerExceptions {
    private final Mapper<ApiResponseDto.StatusInfo, BaseResponseCode> baseResponseCodeToInfoMapper;

    //region bad request
    @ExceptionHandler({EmailNotValid.class})
    public ResponseEntity<ApiResponseDto<Map<String, Object>>> handleEmailNotValidException(RuntimeException err) {
        return handleException(err, HttpStatus.BAD_REQUEST, AuthResponseCode.INVALID_EMAIL);
    }
    //endregion

    //region conflict
    @ExceptionHandler({UserAlreadyExists.class})
    public ResponseEntity<ApiResponseDto<Map<String, Object>>> handleUserAlreadyExistsException(RuntimeException err) {
        return handleException(err, HttpStatus.CONFLICT, AuthResponseCode.USER_ALREADY_EXISTS);
    }
    //endregion

    //region not found
    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<ApiResponseDto<Map<String, Object>>> handleUserNotFoundException(RuntimeException err) {
        return handleException(err, HttpStatus.NOT_FOUND, AuthResponseCode.USER_NOT_FOUND);
    }
    //endregion

    //region unauthorized
    @ExceptionHandler({InvalidToken.class})
    public ResponseEntity<ApiResponseDto<Map<String, Object>>> handleInvalidTokenException(RuntimeException err) {
        return handleException(err, HttpStatus.UNAUTHORIZED, AuthResponseCode.INVALID_TOKEN);
    }
    //endregion

    private ResponseEntity<ApiResponseDto<Map<String, Object>>> handleException(RuntimeException err, HttpStatus status, AuthResponseCode responseCode) {
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


package io.github.saifalhaider.nahrain.nahrain_central_api.common.exceptions;

import io.github.saifalhaider.nahrain.nahrain_central_api.auth.model.responseCode.AuthResponseCode;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.ApiResponseDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.BaseExceptionAdvice;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.BaseResponseCode;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.Mapper;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionAdvice extends BaseExceptionAdvice {

    public CommonExceptionAdvice(Mapper<ApiResponseDto.StatusInfo, BaseResponseCode> baseResponseCodeToInfoMapper) {
        super(baseResponseCodeToInfoMapper);
    }

    @ExceptionHandler({JwtAuthenticationException.class})
    public ResponseEntity<ApiResponseDto<Map<String, Object>>> handleUserNotFound(JwtAuthenticationException err) {
        return handleException(err, HttpStatus.UNAUTHORIZED, AuthResponseCode.USER_NOT_FOUND);
    }
}


package io.github.saifalhaider.nahrain.nahrain_central_api.totp.controller.Exceptions;

import io.github.saifalhaider.nahrain.nahrain_central_api.auth.model.responseCode.AuthResponseCode;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.ApiResponseDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.BaseExceptionAdvice;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.BaseResponseCode;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.Mapper;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TotpExceptionAdvice extends BaseExceptionAdvice {
    public TotpExceptionAdvice(Mapper<ApiResponseDto.StatusInfo, BaseResponseCode> baseResponseCodeToInfoMapper) {
        super(baseResponseCodeToInfoMapper);
    }

    @ExceptionHandler({TwoFactorNotEnabled.class})
    public ResponseEntity<ApiResponseDto<Map<String, Object>>> twoFactorAuthenticationIsNotEnabled(RuntimeException err) {
        return handleException(err, HttpStatus.BAD_REQUEST, AuthResponseCode.TWO_FACTOR_NOT_ENABLED);
    }

    @ExceptionHandler({IncorrectTotp.class})
    public ResponseEntity<ApiResponseDto<Map<String, Object>>> totpIsIncorrect(RuntimeException err) {
        return handleException(err, HttpStatus.BAD_REQUEST, AuthResponseCode.TOTP_IS_INCORRECT);
    }
}

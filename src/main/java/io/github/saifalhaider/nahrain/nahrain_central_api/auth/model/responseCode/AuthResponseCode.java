package io.github.saifalhaider.nahrain.nahrain_central_api.auth.model.responseCode;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.BaseResponseCode;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public enum AuthResponseCode implements BaseResponseCode {
    INVALID_EMAIL(1001, "Invalid email format"),
    INVALID_PASSWORD(1002, "Incorrect password"),
    TOKEN_EXPIRED(1003, "Authentication token has expired"),
    UNAUTHORIZED_ACCESS(1004, "Unauthorized access"),
    REGISTER_SUCCESSFUL(1005, "Register Was Successful"),
    LOGIN_SUCCESSFUL(1006, "Login Was Successful"),
    INVALID_TOKEN(1007, "Invalid Token"),
    REFRESH_TOKEN_CREATED(1008, "Refresh Token Created Successfully"),
    USER_ALREADY_EXISTS(1009, "User already exists"),
    USER_NOT_FOUND(1010, "User Not Found"),
    TWO_FACTOR_NOT_ENABLED(1011, "2FA is Not Enabled"),
    TOTP_IS_INCORRECT(1012, "Incorrect Totp Code"),
    TOTP_VERIFIED(1013, "Totp Verified Successfully"),
    NEW_USER_CREATED(1014, "New User Created Successfully"),
    ;

    private final int code;
    private final String message;

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}

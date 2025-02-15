package io.github.saifalhaider.nahrain.nahrain_central_api.model.dto.responseCode;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public enum AuthResponseCode implements BaseResponseCode {
    INVALID_EMAIL(1001, "Invalid email format"),
    INVALID_PASSWORD(1002, "Incorrect password"),
    TOKEN_EXPIRED(1003, "Authentication token has expired"),
    UNAUTHORIZED_ACCESS(1004, "Unauthorized access"),
    REGISTER_SUCCESSFUL(1005, "Register Was Successful"),
    LOGIN_SUCCESSFUL(1006, "Login Was Successful");

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

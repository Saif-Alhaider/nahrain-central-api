package io.github.saifalhaider.nahrain.nahrain_central_api.totp.controller.Exceptions;

public class TwoFactorNotEnabled extends RuntimeException {
    public TwoFactorNotEnabled(String message) {
        super(message);
    }
}

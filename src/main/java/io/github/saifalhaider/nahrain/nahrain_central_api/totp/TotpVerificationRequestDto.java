package io.github.saifalhaider.nahrain.nahrain_central_api.totp;

import lombok.Data;

@Data
public class TotpVerificationRequestDto {
    private Integer totpCode;
}

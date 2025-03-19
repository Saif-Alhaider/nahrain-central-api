package io.github.saifalhaider.nahrain.nahrain_central_api.totp.controller;

import com.google.zxing.WriterException;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.ApiResponseDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.exceptions.JwtAuthenticationException;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.MessageDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.totp.TotpVerificationRequestDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.totp.service.TotpGeneratorService;
import io.github.saifalhaider.nahrain.nahrain_central_api.totp.service.TotpValidatorService;
import java.awt.image.BufferedImage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class TotpController {

    private final TotpGeneratorService totpGeneratorService;
    private final TotpValidatorService totpValidatorService;
    @Value("${frontend.url}")
    private String FRONTEND_URL;

    @GetMapping(value = {"/totp"}, produces = {MediaType.IMAGE_PNG_VALUE})
    public BufferedImage generateTotp() throws WriterException, JwtAuthenticationException {
        return totpGeneratorService
                .generateQrCode();
    }

    @PostMapping("/verify-totp")
    public ResponseEntity<ApiResponseDto<MessageDto>> verifyTotp(@RequestBody TotpVerificationRequestDto request) {
        return totpValidatorService.verifyTotp(request.getTotpCode().toString());
    }

}

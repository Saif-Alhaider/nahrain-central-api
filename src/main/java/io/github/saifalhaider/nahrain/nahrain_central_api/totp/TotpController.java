package io.github.saifalhaider.nahrain.nahrain_central_api.totp;

import com.google.zxing.WriterException;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.exceptions.JwtAuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;

@RestController
@RequestMapping("api/v1/totp")
@CrossOrigin
@RequiredArgsConstructor
public class TotpController {

    private final TotpService totpService;

    @GetMapping(value = {"", "/"}, produces = {MediaType.IMAGE_PNG_VALUE})
    public BufferedImage generateTotp() throws WriterException, JwtAuthenticationException {
        return totpService.generateQrCode();
    }
}

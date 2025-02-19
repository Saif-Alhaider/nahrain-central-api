package io.github.saifalhaider.nahrain.nahrain_central_api.totp;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import dev.turingcomplete.kotlinonetimepassword.GoogleAuthenticator;
import lombok.val;
import org.apache.commons.codec.binary.Base32;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@RestController
@RequestMapping("api/v1/totp")
@CrossOrigin
public class TotpController {

    @GetMapping(value = {"", "/"}, produces = {MediaType.IMAGE_PNG_VALUE})
    public BufferedImage generateTotp() throws WriterException {
        return generate("NahrainCentral", "example@mail.com", generateTotpCode());
    }

    public static BufferedImage generate(String issuer, String email, String secret) throws WriterException {
        String uri = String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s",
                URLEncoder.encode(issuer, StandardCharsets.UTF_8),
                URLEncoder.encode(email, StandardCharsets.UTF_8),
                secret,
                URLEncoder.encode(issuer, StandardCharsets.UTF_8));

        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix matrix = writer.encode(uri, BarcodeFormat.QR_CODE, 200, 200);

        return MatrixToImageWriter.toBufferedImage(matrix);
    }

    public static String generateTotpCode() {
        byte[] secretBytes = GoogleAuthenticator.Companion.createRandomSecretAsByteArray();
        return new Base32().encodeToString(secretBytes).replace("=", ""); // Remove padding
    }
}

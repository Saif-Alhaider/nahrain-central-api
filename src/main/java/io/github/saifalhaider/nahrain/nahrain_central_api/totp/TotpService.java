package io.github.saifalhaider.nahrain.nahrain_central_api.totp;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import dev.turingcomplete.kotlinonetimepassword.GoogleAuthenticator;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.exceptions.JwtAuthenticationException;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.apache.commons.codec.binary.Base32;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class TotpService {
    private final UserRepository userRepository;

    public BufferedImage generateQrCode() throws WriterException, JwtAuthenticationException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        val secrete = generateTotpSecret();
        userRepository.updateTotpSecret(auth.getName(), secrete);
        return generateQrCodeBufferedImage(auth, generateTotpSecret());
    }

    private BufferedImage generateQrCodeBufferedImage(Authentication auth, String secret) throws WriterException {
        String uri = generateTotpUri("NahrainCentral", auth.getName(), secret);

        QRCodeWriter writer = new QRCodeWriter(); //todo rely on dependency instead of actual concrete class
        BitMatrix matrix = writer.encode(uri, BarcodeFormat.QR_CODE, 200, 200);

        return MatrixToImageWriter.toBufferedImage(matrix);
    }

    private static String generateTotpUri(String issuer, String email, String secret) {
        return String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s",
                URLEncoder.encode(issuer, StandardCharsets.UTF_8),
                URLEncoder.encode(email, StandardCharsets.UTF_8),
                secret,
                URLEncoder.encode(issuer, StandardCharsets.UTF_8));
    }

    private String generateTotpSecret() {
        byte[] secretBytes = GoogleAuthenticator.Companion.createRandomSecretAsByteArray();
        return new Base32().encodeToString(secretBytes).replace("=", "");
    }
}

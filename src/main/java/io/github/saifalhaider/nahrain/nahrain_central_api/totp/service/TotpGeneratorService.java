package io.github.saifalhaider.nahrain.nahrain_central_api.totp.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.exceptions.JwtAuthenticationException;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.User;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.repository.user.UserRepository;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TotpGeneratorService {
    private final UserRepository<User,Integer> userRepository;

    public BufferedImage generateQrCode() throws WriterException, JwtAuthenticationException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        val secrete = generateTotpSecret();
        userRepository.updateTotpSecret(auth.getName(), secrete);
        return generateQrCodeBufferedImage(auth, secrete);
    }

    private BufferedImage generateQrCodeBufferedImage(Authentication auth, String secret) throws WriterException {
        String uri = "otpauth://totp/NahrainCentral:" + auth.getName() + "?secret=" + secret + "&issuer=NahrainCentral";

        QRCodeWriter writer = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        BitMatrix matrix = writer.encode(uri, BarcodeFormat.QR_CODE, 200, 200, hints);
        return MatrixToImageWriter.toBufferedImage(matrix);
    }


    private String generateTotpSecret() {
        return new DefaultSecretGenerator().generate();
    }
}

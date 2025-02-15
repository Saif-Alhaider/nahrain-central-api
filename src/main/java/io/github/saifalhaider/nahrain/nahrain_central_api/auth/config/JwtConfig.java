package io.github.saifalhaider.nahrain.nahrain_central_api.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Configuration
public class JwtConfig {

    @Value("${app.key.secrete}")
    private String SECRET_KEY;

    public SecretKey getSignInKey() {
        byte[] bytes = Base64.getDecoder().decode(SECRET_KEY);
        return new SecretKeySpec(bytes, "HmacSHA256");
    }
}

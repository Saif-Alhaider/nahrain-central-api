package io.github.saifalhaider.nahrain.nahrain_central_api.auth.model.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseCookie;

@Data
@Builder
public class AuthIssue {
    private String token;
    private ResponseCookie refreshToken;
}

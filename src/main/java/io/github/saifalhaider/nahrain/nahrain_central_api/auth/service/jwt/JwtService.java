package io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.jwt;

import io.github.saifalhaider.nahrain.nahrain_central_api.auth.config.JwtConfig;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtConfig jwtConfig;
    final Integer TOKEN_VALIDITY = 7 * 24 * 60 * 60 * 1000;

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY))
                .signWith(jwtConfig.getSignInKey())
                .compact();
    }

    //todo add generate ref token
}

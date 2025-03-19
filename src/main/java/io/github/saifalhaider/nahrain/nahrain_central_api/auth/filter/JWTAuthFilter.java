package io.github.saifalhaider.nahrain.nahrain_central_api.auth.filter;

import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.exception.InvalidToken;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.jwt.JwtHelper;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.validation.jwt.JwtValidator;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.exceptions.JwtAuthenticationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JWTAuthFilter extends OncePerRequestFilter {
    private final JwtHelper jwtHelper;
    private final UserDetailsService userDetailsService;
    private final JwtValidator jwtValidator;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwtToken = getJWTTokenFromHeader(authHeader);
        final String username = jwtHelper.getUserNameClaim(jwtToken);

        if (username == null) {
            throw new JwtAuthenticationException("Invalid JWT token: No username found");
        }

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserDetails user = userDetailsService.loadUserByUsername(username);
                if (!jwtValidator.isJwtValid(jwtToken, user)) {
                    throw new InvalidToken(jwtToken, "Invalid JWT token");
                }

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

            } catch (Exception e) {
                throw new JwtAuthenticationException("Authentication failed");
            }
        }

        filterChain.doFilter(request, response);
    }


    private String getJWTTokenFromHeader(@NonNull String header) {
        return header.startsWith("Bearer ")
                ? header.substring(7)
                : null;
    }
}

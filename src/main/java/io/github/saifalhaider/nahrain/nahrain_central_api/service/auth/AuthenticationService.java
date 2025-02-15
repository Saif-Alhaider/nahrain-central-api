package io.github.saifalhaider.nahrain.nahrain_central_api.service.auth;

import io.github.saifalhaider.nahrain.nahrain_central_api.entity.User;
import io.github.saifalhaider.nahrain.nahrain_central_api.repository.UserRepository;
import io.github.saifalhaider.nahrain.nahrain_central_api.dto.authDto.AuthenticationRequest;
import io.github.saifalhaider.nahrain.nahrain_central_api.dto.authDto.AuthenticationResponse;
import io.github.saifalhaider.nahrain.nahrain_central_api.dto.authDto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .fullName(request.getEmail())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(User.Role.PROF)
                .build();

        userRepository.save(user);

        var jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwt).build();
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),
                        request.getPassword())
        );

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(""));

        val jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}

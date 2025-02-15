package io.github.saifalhaider.nahrain.nahrain_central_api.config;

import io.github.saifalhaider.nahrain.nahrain_central_api.dto.authDto.RegisterRequestDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.entity.User;
import io.github.saifalhaider.nahrain.nahrain_central_api.repository.UserRepository;
import io.github.saifalhaider.nahrain.nahrain_central_api.service.auth.EmailValidator;
import io.github.saifalhaider.nahrain.nahrain_central_api.service.auth.NahrainEmailValidatorImpl;
import io.github.saifalhaider.nahrain.nahrain_central_api.service.mapper.Mapper;
import io.github.saifalhaider.nahrain.nahrain_central_api.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    private final UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public EmailValidator emailValidator() {
        return new NahrainEmailValidatorImpl();
    }

    @Bean
    public Mapper<User, RegisterRequestDto> userMapper() {
        return new UserMapper(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

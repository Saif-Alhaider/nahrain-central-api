package io.github.saifalhaider.nahrain.nahrain_central_api.common.config;

import io.github.saifalhaider.nahrain.nahrain_central_api.auth.Repository.RefreshTokenRepository;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.config.JwtConfig;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.model.dto.RegisterRequestDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.handler.JwtAccessTokenHandler;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.handler.RefreshTokenHandler;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.jwt.JwtHelper;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.jwt.JwtHelperImpl;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.mapper.BaseResponseCodeToInfoMapper;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.validation.email.EmailValidator;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.validation.email.NahrainEmailValidatorImpl;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.validation.jwt.JwtValidator;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.validation.jwt.JwtValidatorImpl;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.ApiResponseDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.BaseResponseCode;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.Mapper;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.User;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.repository.UserRepository;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.service.mapper.UserMapper;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.util.cookie.CookieUtil;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.util.cookie.CookieUtilImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.awt.image.BufferedImage;

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
    public Mapper<ApiResponseDto.StatusInfo, BaseResponseCode> baseResponseCodeMapper() {
        return new BaseResponseCodeToInfoMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtHelper jwtHelper(JwtConfig jwtConfig) {
        return new JwtHelperImpl(jwtConfig);
    }

    @Bean
    public JwtValidator jwtValidator(JwtHelper jwtHelper) {
        return new JwtValidatorImpl(jwtHelper);
    }

    @Bean
    public CookieUtil cookieUtil() {
        return new CookieUtilImpl();
    }

    @Bean
    public RefreshTokenHandler refreshTokenHandler(CookieUtil cookieUtil, RefreshTokenRepository refreshTokenRepository) {
        return new RefreshTokenHandler(cookieUtil, refreshTokenRepository);
    }

    @Bean
    public JwtAccessTokenHandler jwtAccessTokenHandler(JwtConfig jwtConfig) {
        return new JwtAccessTokenHandler(jwtConfig);
    }

    @Bean
    public HttpMessageConverter<BufferedImage> imageConverter() {
        return new BufferedImageHttpMessageConverter();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:3000");
            }
        };
    }
}

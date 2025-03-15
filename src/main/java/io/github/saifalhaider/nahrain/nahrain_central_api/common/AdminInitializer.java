package io.github.saifalhaider.nahrain.nahrain_central_api.common;

import io.github.saifalhaider.nahrain.nahrain_central_api.auth.service.validation.email.EmailValidator;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.Admin;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.User;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {
    private final UserRepository<User, Integer> userRepository;
    @Value("${admin.full_name}")
    private String fullName;
    @Value("${admin.email}")
    private String email;
    @Value("${admin.password}")
    private String password;
    private final PasswordEncoder passwordEncoder;
    private final EmailValidator emailValidator;


    @Override
    public void run(String... args) {
        val adminEmail = emailValidator.getCompletedEmail(email);
        if (userRepository.findByEmail(adminEmail).isEmpty()) {
            Admin user = Admin.builder().email(adminEmail).password(passwordEncoder.encode(password)).fullName(fullName).build();
            userRepository.save(user);
        }
    }
}

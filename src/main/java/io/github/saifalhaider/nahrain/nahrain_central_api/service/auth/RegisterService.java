package io.github.saifalhaider.nahrain.nahrain_central_api.service.auth;

import io.github.saifalhaider.nahrain.nahrain_central_api.dto.authDto.AuthenticationResponseDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.dto.authDto.RegisterRequestDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.entity.User;
import io.github.saifalhaider.nahrain.nahrain_central_api.repository.UserRepository;
import io.github.saifalhaider.nahrain.nahrain_central_api.service.auth.exception.EmailNotValid;
import io.github.saifalhaider.nahrain.nahrain_central_api.service.auth.exception.UserAlreadyExists;
import io.github.saifalhaider.nahrain.nahrain_central_api.service.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final EmailValidator emailValidator;
    private final Mapper<User,RegisterRequestDto> userMapper;

    public AuthenticationResponseDto register(RegisterRequestDto request) throws UserAlreadyExists, EmailNotValid {
        validateRegisterRequest(request);

        val user = userMapper.toEntity(request);

        userRepository.save(user);

        val jwt = jwtService.generateToken(user);
        return AuthenticationResponseDto.builder().token(jwt).build();
    }

    private void validateRegisterRequest(RegisterRequestDto request) throws UserAlreadyExists, EmailNotValid {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExists("The user with email " + request.getEmail() + " already exists");
        } else if (!emailValidator.isValid(request.getEmail())) {
            throw new EmailNotValid("The email must belong to nahrainuniv.edu.iq domain");
        }
    }
}

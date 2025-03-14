package io.github.saifalhaider.nahrain.nahrain_central_api.common.service.mapper;

import io.github.saifalhaider.nahrain.nahrain_central_api.auth.model.dto.RegisterRequestDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.Mapper;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.User;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.UserFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@RequiredArgsConstructor
public class UserMapper implements Mapper<User, RegisterRequestDto> {
    private final PasswordEncoder passwordEncoder;

    @Override
    public User toEntity(RegisterRequestDto registerRequestDto) {
        return UserFactory.createUser(
                registerRequestDto.getFullName(),
                registerRequestDto.getEmail(),
                passwordEncoder.encode(registerRequestDto.getPassword()),
                null,
                null,
                null
        );
    }

    @Override
    public List<User> toEntityList(List<RegisterRequestDto> registerRequestDtos) {
        return registerRequestDtos.stream().map(this::toEntity).toList();
    }
}

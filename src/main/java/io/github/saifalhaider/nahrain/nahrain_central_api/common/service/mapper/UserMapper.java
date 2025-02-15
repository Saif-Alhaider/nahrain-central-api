package io.github.saifalhaider.nahrain.nahrain_central_api.common.service.mapper;

import io.github.saifalhaider.nahrain.nahrain_central_api.auth.model.dto.RegisterRequestDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.Mapper;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@RequiredArgsConstructor
public class UserMapper implements Mapper<User, RegisterRequestDto> {
    private final PasswordEncoder passwordEncoder;

    @Override
    public User toEntity(RegisterRequestDto registerRequestDto) {
        return User.builder()
                .fullName(null)
                .email(registerRequestDto.getEmail())
                .password(passwordEncoder.encode(registerRequestDto.getPassword()))
                .role(null)
                .build();
    }

    @Override
    public List<User> toEntityList(List<RegisterRequestDto> registerRequestDtos) {
        return registerRequestDtos.stream().map(this::toEntity).toList();
    }
}

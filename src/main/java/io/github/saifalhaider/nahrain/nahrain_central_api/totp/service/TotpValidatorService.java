package io.github.saifalhaider.nahrain.nahrain_central_api.totp.service;

import dev.samstevens.totp.code.CodeGenerator;
import dev.samstevens.totp.code.CodeVerifier;
import dev.samstevens.totp.code.DefaultCodeGenerator;
import dev.samstevens.totp.code.DefaultCodeVerifier;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.time.TimeProvider;
import io.github.saifalhaider.nahrain.nahrain_central_api.auth.model.responseCode.AuthResponseCode;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.ApiResponseDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.BaseResponseCode;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.Mapper;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.MessageDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.User;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.repository.UserRepository;
import io.github.saifalhaider.nahrain.nahrain_central_api.totp.controller.Exceptions.IncorrectTotp;
import io.github.saifalhaider.nahrain.nahrain_central_api.totp.controller.Exceptions.TwoFactorNotEnabled;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TotpValidatorService {
    private final UserRepository<User,Integer> userRepository;
    private final Mapper<ApiResponseDto.StatusInfo, BaseResponseCode> baseResponseCodeToInfoMapper;


    public ResponseEntity<ApiResponseDto<MessageDto>> verifyTotp(String totp) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String secretKey = user.getTotpSecret();
        if (secretKey == null) {
            throw new TwoFactorNotEnabled("Secret key is null");
        }
        if (isValid(user.getTotpSecret(), totp)) {
            userRepository.updateMfaEnabled(user.getEmail(), true);
            val statusInfo = baseResponseCodeToInfoMapper.toEntity(AuthResponseCode.TOTP_VERIFIED);
            val payload = MessageDto.builder().message("Totp Verified Successfully").build();

            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponseDto.response(statusInfo, payload));
        } else {
            throw new IncorrectTotp("totp code is incorrect");
        }
    }

    public boolean isValid(String secret, String code) {
        TimeProvider time = new SystemTimeProvider();
        CodeGenerator codeGenerator = new DefaultCodeGenerator();
        CodeVerifier codeVerifier = new DefaultCodeVerifier(codeGenerator, time);
        return codeVerifier.isValidCode(secret, code);
    }
}

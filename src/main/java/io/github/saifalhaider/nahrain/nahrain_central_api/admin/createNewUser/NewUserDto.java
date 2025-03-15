package io.github.saifalhaider.nahrain.nahrain_central_api.admin.createNewUser;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.GenderDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewUserDto {
    private String fullName;
    private LocalDate birthDate;
    private String email;
    private GenderDto gender;
    private String phoneNumber;
    private String province;
    private User.Role role;
}



package io.github.saifalhaider.nahrain.nahrain_central_api.common.service.mapper;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.Mapper;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.ProfDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.Prof;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProfDtoMapper implements Mapper<ProfDto, Prof> {
    @Override
    public ProfDto mapToDomain(Prof prof) {
        return ProfDto.builder()
                .id(prof.getId())
                .email(prof.getEmail())
                .date(prof.getCreatedDate())
                .fullName(prof.getFullName())
                .build();
    }

    @Override
    public List<ProfDto> mapToDomainList(List<Prof> profs) {
        return profs.stream().map(this::mapToDomain).toList();
    }
}

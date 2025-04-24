package io.github.saifalhaider.nahrain.nahrain_central_api.prof.attendance;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.CurriculumDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class AttendanceSessionsDto {
    private CurriculumDto curriculum;
    private LocalDate sessionDate;
}

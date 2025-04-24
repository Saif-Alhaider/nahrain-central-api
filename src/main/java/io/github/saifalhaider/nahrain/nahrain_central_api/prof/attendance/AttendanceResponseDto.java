package io.github.saifalhaider.nahrain.nahrain_central_api.prof.attendance;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AttendanceResponseDto {
  private Integer studentId;
  private String studentName;
  private String status;
  private LocalDate attendanceDate;
  private Double absentHours;
}

package io.github.saifalhaider.nahrain.nahrain_central_api.prof.attendance;

import java.time.LocalDate;

public class AttendanceRequestDto {
  public Long curriculumId;
  public Integer studentId;
  public LocalDate attendanceDate;
  public Double absentHours;
}

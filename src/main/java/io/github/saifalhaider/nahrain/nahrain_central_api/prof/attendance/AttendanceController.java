package io.github.saifalhaider.nahrain.nahrain_central_api.prof.attendance;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.ApiResponseDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.Prof;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.repository.user.ProfRepository;
import java.nio.file.AccessDeniedException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/prof/attendance")
@RequiredArgsConstructor
public class AttendanceController {

  private final AttendanceService attendanceService;
  private final ProfRepository profRepository;

  @GetMapping
  public ApiResponseDto<List<AttendanceSessionsDto>> getCurriculumAttendance() throws AccessDeniedException {

    Prof prof = getAuthenticatedProf();
    List<AttendanceSessionsDto> attendanceRecords =
            attendanceService.getProfessorAttendanceSessions(prof);

    return ApiResponseDto.response(
            ApiResponseDto.StatusInfo.builder()
                    .code(HttpStatus.OK.value())
                    .message("Attendance records retrieved successfully")
                    .build(),
            attendanceRecords);
  }

  @PostMapping
  public ApiResponseDto<AttendanceResponseDto> recordAttendance(
      @RequestBody AttendanceRequestDto request) throws AccessDeniedException {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();

    Prof prof =
        profRepository
            .findByEmail(email)
            .orElseThrow(() -> new AccessDeniedException("Professor not found"));

    AttendanceResponseDto response = attendanceService.recordAttendance(prof, request);

    return ApiResponseDto.response(
        ApiResponseDto.StatusInfo.builder()
            .code(HttpStatus.OK.value())
            .message("Attendance recorded successfully")
            .build(),
        response);
  }

  @GetMapping("/student/{studentId}")
  public ApiResponseDto<List<AttendanceResponseDto>> getStudentAttendance(
      @PathVariable Integer studentId) throws AccessDeniedException {

    Prof prof = getAuthenticatedProf();
    List<AttendanceResponseDto> attendanceRecords =
        attendanceService.getStudentAttendance(prof, studentId);

    return ApiResponseDto.response(
        ApiResponseDto.StatusInfo.builder()
            .code(HttpStatus.OK.value())
            .message("Attendance records retrieved successfully")
            .build(),
        attendanceRecords);
  }


  private Prof getAuthenticatedProf() throws AccessDeniedException {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();
    return profRepository
        .findByEmail(email)
        .orElseThrow(() -> new AccessDeniedException("Professor not found"));
  }
}

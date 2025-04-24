package io.github.saifalhaider.nahrain.nahrain_central_api.prof.attendance;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.CurriculumDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.attendance.Attendance;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.attendance.AttendanceStatus;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.curriculum.Curriculum;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.Prof;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.Student;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.repository.AttendanceRepository;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.repository.CurriculumRepository;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.repository.user.StudentRepository;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.service.mapper.CurriculumDtoMapper;
import jakarta.persistence.EntityNotFoundException;
import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttendanceService {
  private final AttendanceRepository attendanceRepository;
  private final CurriculumRepository curriculumRepository;
  private final StudentRepository studentRepository;
  private final CurriculumDtoMapper curriculumDtoMapper;

  public AttendanceResponseDto recordAttendance(Prof prof, AttendanceRequestDto request)
      throws AccessDeniedException {
    Curriculum curriculum = validateCurriculumAndProfessor(prof, request.curriculumId);
    Student student = validateStudent(request.studentId, curriculum);
    Attendance attendance = createOrUpdateAttendance(prof, request, curriculum, student);
    return buildAttendanceResponse(student, attendance);
  }

  private Curriculum validateCurriculumAndProfessor(Prof prof, Long curriculumId)
      throws AccessDeniedException {
    Curriculum curriculum =
        curriculumRepository
            .findById(curriculumId)
            .orElseThrow(() -> new EntityNotFoundException("Curriculum not found"));

    if (!curriculum.getProfs().contains(prof)) {
      throw new AccessDeniedException("Professor not assigned to this curriculum");
    }

    return curriculum;
  }

  private Student validateStudent(Integer studentId, Curriculum curriculum) {
    Student student =
        studentRepository
            .findById(studentId)
            .orElseThrow(() -> new EntityNotFoundException("Student not found"));

    if (!student.getStage().equals(curriculum.getStage())) {
      throw new IllegalArgumentException("Student not in curriculum's stage");
    }

    return student;
  }

  private Attendance createOrUpdateAttendance(
      Prof prof, AttendanceRequestDto request, Curriculum curriculum, Student student) {
    Attendance attendance = findExistingAttendance(request).orElse(new Attendance());

    updateAttendanceFields(attendance, prof, request, curriculum, student);
    return attendanceRepository.save(attendance);
  }

  private Optional<Attendance> findExistingAttendance(AttendanceRequestDto request) {
    return attendanceRepository.findByCurriculumIdAndStudentIdAndAttendanceDate(
        request.curriculumId, request.studentId, request.attendanceDate);
  }

  private void updateAttendanceFields(
      Attendance attendance,
      Prof prof,
      AttendanceRequestDto request,
      Curriculum curriculum,
      Student student) {
    attendance.setCurriculum(curriculum);
    attendance.setStudent(student);
    attendance.setAttendanceDate(request.attendanceDate);
    attendance.setStatus(
        request.absentHours != null ? AttendanceStatus.ABSENT : AttendanceStatus.PRESENT);
    attendance.setAbsentHours(request.absentHours);
    attendance.setRecordedBy(prof);
  }

  private AttendanceResponseDto buildAttendanceResponse(Student student, Attendance attendance) {
    return AttendanceResponseDto.builder()
        .studentId(student.getId())
        .studentName(student.getFullName())
        .status(attendance.getStatus().toString())
        .attendanceDate(attendance.getAttendanceDate())
        .absentHours(attendance.getAbsentHours())
        .build();
  }

  public List<AttendanceResponseDto> getStudentAttendance(Prof prof, Integer studentId)
      throws AccessDeniedException {
    Student student =
        studentRepository
            .findById(studentId)
            .orElseThrow(() -> new EntityNotFoundException("Student not found"));

    List<Curriculum> professorCurriculums =
        curriculumRepository.findByProfsContainingAndStage(prof, student.getStage());

    if (professorCurriculums.isEmpty()) {
      throw new AccessDeniedException("Professor doesn't teach this student's stage");
    }

    List<Attendance> attendanceRecords = attendanceRepository.findByStudent(student);

    return attendanceRecords.stream()
        .map(attendance -> buildAttendanceResponse(student, attendance))
        .collect(Collectors.toList());
  }

  public List<AttendanceSessionsDto> getProfessorAttendanceSessions(Prof prof) {
    List<Curriculum> professorCurriculums = curriculumRepository.findByProfsContaining(prof);

    return professorCurriculums.stream()
            .flatMap(curriculum -> {
              CurriculumDto curriculumDto = curriculumDtoMapper.mapTo(curriculum);

              List<LocalDate> attendanceDates = attendanceRepository
                      .findDistinctAttendanceDatesByCurriculum(curriculum);

              return attendanceDates.stream()
                      .map(date -> AttendanceSessionsDto.builder()
                              .curriculum(curriculumDto)
                              .sessionDate(date)
                              .build());
            })
            .collect(Collectors.toList());
  }
}

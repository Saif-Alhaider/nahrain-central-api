package io.github.saifalhaider.nahrain.nahrain_central_api.common.repository;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.attendance.Attendance;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.curriculum.Curriculum;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByCurriculumIdAndStudentIdAndAttendanceDate(
            Long curriculumId,
            Integer studentId,
            LocalDate attendanceDate);
    List<Attendance> findByStudent(Student student);

    @Query("SELECT DISTINCT a.attendanceDate FROM Attendance a WHERE a.curriculum = :curriculum")
    List<LocalDate> findDistinctAttendanceDatesByCurriculum(@Param("curriculum") Curriculum curriculum);
}

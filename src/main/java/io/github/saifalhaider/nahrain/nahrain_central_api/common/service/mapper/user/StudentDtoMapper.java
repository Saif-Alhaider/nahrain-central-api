package io.github.saifalhaider.nahrain.nahrain_central_api.common.service.mapper.user;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.Mapper;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.StudentDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.user.Student;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentDtoMapper implements Mapper<StudentDto, Student> {
    @Override
    public StudentDto mapTo(Student student) {
        return StudentDto.builder()
                .id(student.getId())
                .email(student.getEmail())
                .date(student.getCreatedDate())
                .fullName(student.getFullName())
                .build();
    }

    @Override
    public List<StudentDto> mapToList(List<Student> students) {
        return students.stream().map(this::mapTo).toList();
    }
}

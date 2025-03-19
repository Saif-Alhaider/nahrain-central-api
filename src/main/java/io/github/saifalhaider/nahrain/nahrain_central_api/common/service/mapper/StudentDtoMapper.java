package io.github.saifalhaider.nahrain.nahrain_central_api.common.service.mapper;

import io.github.saifalhaider.nahrain.nahrain_central_api.common.base.Mapper;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.dto.StudentDto;
import io.github.saifalhaider.nahrain.nahrain_central_api.common.model.entity.Student;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentDtoMapper implements Mapper<StudentDto, Student> {
    @Override
    public StudentDto mapToDomain(Student student) {
        return StudentDto.builder()
                .id(student.getId())
                .email(student.getEmail())
                .date(student.getCreatedDate())
                .fullName(student.getFullName())
                .build();
    }

    @Override
    public List<StudentDto> mapToDomainList(List<Student> students) {
        return students.stream().map(this::mapToDomain).toList();
    }
}

package com.capgemini.gradebook.domain.mapper;

import com.capgemini.gradebook.domain.StudentEto;
import com.capgemini.gradebook.persistence.entity.ClassYearEntity;
import com.capgemini.gradebook.persistence.entity.StudentEntity;
import com.capgemini.gradebook.service.support.AbstractServiceSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public final class StudentMapper extends AbstractServiceSupport {


    public StudentEto mapToETO(StudentEntity entity) {

        StudentEto student = new StudentEto();
        student.setId(entity.getId());
        student.setVersion(entity.getVersion());
        student.setCreateDate(entity.getCreateDate());
        student.setUpdateDate(entity.getUpdateDate());
        student.setFirstName(entity.getFirstName());
        student.setLastName(entity.getLastName());
        student.setAge(entity.getAge());
        if (entity.getClassYear() != null) {
            student.setClassYearId(entity.getClassYear().getId());
        }
        return student;
    }

    public StudentEntity mapToEntity(StudentEto studentEto) {
        StudentEntity entity = new StudentEntity();

        entity.setId(studentEto.getId());
        entity.setVersion(studentEto.getVersion());
        entity.setCreateDate(studentEto.getCreateDate());
        entity.setUpdateDate(studentEto.getUpdateDate());
        entity.setFirstName(studentEto.getFirstName());
        entity.setLastName(studentEto.getLastName());
        entity.setAge(studentEto.getAge());

        ClassYearEntity classYearEntity = getClassYearMapper()
                .mapToEntity(getClassYearService().findClassYearById(studentEto.getClassYearId()));

        entity.setClassYear(classYearEntity);

        return entity;
    }

    public List<StudentEto> mapToETOList(List<StudentEntity> entities) {
        return entities.stream().map(this::mapToETO).collect(Collectors.toList());
    }
}

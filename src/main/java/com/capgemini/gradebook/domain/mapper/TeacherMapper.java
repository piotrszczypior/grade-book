package com.capgemini.gradebook.domain.mapper;

import com.capgemini.gradebook.domain.TeacherEto;
import com.capgemini.gradebook.persistence.entity.TeacherEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public final class TeacherMapper {

    public TeacherEto mapToETO(TeacherEntity entity) {

        TeacherEto teacher = new TeacherEto();
        teacher.setId(entity.getId());
        teacher.setVersion(entity.getVersion());
        teacher.setCreateDate(entity.getCreateDate());
        teacher.setUpdateDate(entity.getUpdateDate());
        teacher.setFirstName(entity.getFirstName());
        teacher.setLastName(entity.getLastName());
        return teacher;
    }

    public TeacherEntity mapToEntity(TeacherEto teacherTo) {

        TeacherEntity entity = new TeacherEntity();
        entity.setId(teacherTo.getId());
        entity.setVersion(teacherTo.getVersion());
        entity.setCreateDate(teacherTo.getCreateDate());
        entity.setUpdateDate(teacherTo.getUpdateDate());
        entity.setFirstName(teacherTo.getFirstName());
        entity.setLastName(teacherTo.getLastName());
        return entity;
    }

    public List<TeacherEto> mapToETOList(List<TeacherEntity> entities) {
        return entities.stream().map(this::mapToETO).collect(Collectors.toList());
    }

    public List<TeacherEntity> mapToEntityList(List<TeacherEto> tos) {
        return tos.stream().map(this::mapToEntity).collect(Collectors.toList());
    }

}

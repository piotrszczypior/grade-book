package com.capgemini.gradebook.domain.mapper;

import com.capgemini.gradebook.domain.SubjectEto;
import com.capgemini.gradebook.persistence.entity.ClassYearEntity;
import com.capgemini.gradebook.persistence.entity.SubjectEntity;
import com.capgemini.gradebook.persistence.entity.TeacherEntity;
import com.capgemini.gradebook.service.support.AbstractServiceSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public final class SubjectMapper extends AbstractServiceSupport {

    public SubjectEto mapToETO(SubjectEntity entity) {

        SubjectEto subject = new SubjectEto();
        subject.setId(entity.getId());
        subject.setVersion(entity.getVersion());
        subject.setCreateDate(entity.getCreateDate());
        subject.setUpdateDate(entity.getUpdateDate());
        subject.setSubjectType(entity.getSubjectType());

        if (entity.getClassYear() != null) {
            subject.setClassYearId(entity.getClassYear().getId());
        }

        if (entity.getTeacher() != null) {
            subject.setTeacherId(entity.getTeacher().getId());
        }

        subject.setSubjectName(entity.getName());

        return subject;
    }

    public SubjectEntity mapToEntity(SubjectEto subjectEto) {
        SubjectEntity entity = new SubjectEntity();
        entity.setId(subjectEto.getId());
        entity.setVersion(subjectEto.getVersion());
        entity.setCreateDate(subjectEto.getCreateDate());
        entity.setUpdateDate(subjectEto.getUpdateDate());
        entity.setSubjectType(subjectEto.getSubjectType());
        entity.setName(subjectEto.getSubjectName());

        TeacherEntity teacherEntity = getTeacherMapper()
                .mapToEntity(getTeacherService().findTeacherById(subjectEto.getTeacherId()));

        entity.setTeacher(teacherEntity);

        ClassYearEntity classYearEntity = getClassYearMapper()
                .mapToEntity(getClassYearService().findClassYearById(subjectEto.getClassYearId()));

        entity.setClassYear(classYearEntity);

        return entity;
    }

    public List<SubjectEto> mapToETOList(List<SubjectEntity> entities) {
        return entities.stream().map(this::mapToETO).collect(Collectors.toList());
    }

    public List<SubjectEntity> mapToEntityList(List<SubjectEto> subjectTos) {
        return subjectTos.stream().map(this::mapToEntity).collect(Collectors.toList());
    }
}

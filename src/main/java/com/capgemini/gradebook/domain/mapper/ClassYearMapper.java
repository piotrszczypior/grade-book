package com.capgemini.gradebook.domain.mapper;

import com.capgemini.gradebook.domain.ClassYearEto;
import com.capgemini.gradebook.persistence.entity.ClassYearEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public final class ClassYearMapper {

    public ClassYearEto mapToETO(ClassYearEntity entity) {
        ClassYearEto classYear = new ClassYearEto();
        classYear.setId(entity.getId());
        classYear.setVersion(entity.getVersion());
        classYear.setCreateDate(entity.getCreateDate());
        classYear.setUpdateDate(entity.getUpdateDate());
        classYear.setClassYear(entity.getClassYear());
        classYear.setClassName(entity.getClassName());
        classYear.setClassLevel(entity.getClassLevel());

        return classYear;
    }

    public ClassYearEntity mapToEntity(ClassYearEto classYearEto) {
        ClassYearEntity entity = new ClassYearEntity();
        entity.setId(classYearEto.getId());
        entity.setVersion(classYearEto.getVersion());
        entity.setCreateDate(classYearEto.getCreateDate());
        entity.setUpdateDate(classYearEto.getUpdateDate());
        entity.setClassYear(classYearEto.getClassYear());
        entity.setClassLevel(classYearEto.getClassLevel());
        entity.setClassName(classYearEto.getClassName());

        return entity;
    }

    public List<ClassYearEto> mapToETOList(List<ClassYearEntity> entities) {
        return entities.stream().map(this::mapToETO).collect(Collectors.toList());
    }

    public List<ClassYearEntity> mapToEntityList(List<ClassYearEto> subjectTos) {
        return subjectTos.stream().map(this::mapToEntity).collect(Collectors.toList());
    }
}

package com.capgemini.gradebook.domain.mapper;

import com.capgemini.gradebook.domain.GradeEto;
import com.capgemini.gradebook.exception.MapperException;
import com.capgemini.gradebook.persistence.entity.GradeEntity;
import com.capgemini.gradebook.persistence.entity.StudentEntity;
import com.capgemini.gradebook.persistence.entity.SubjectEntity;
import com.capgemini.gradebook.persistence.entity.TeacherEntity;
import com.capgemini.gradebook.service.support.AbstractServiceSupport;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public final class GradeMapper extends AbstractServiceSupport {

    public <S, T> T map(S source, Class<T> destinationType) {
        try {
            T destination = destinationType.getDeclaredConstructor().newInstance();

            List<Field> fields = new ArrayList<>();
            fields.addAll(List.of(destinationType.getSuperclass().getDeclaredFields()));
            fields.addAll(List.of(destinationType.getDeclaredFields()));

            for (Field field : fields) {
                if (Modifier.isFinal(field.getModifiers())) {
                    continue;
                }
                Object object;

                String fieldName = field.getName();
                if (fieldName.endsWith("Id")) {
                    String destinationName = field.getName().substring(0, fieldName.length() - 2);
                    object = new PropertyDescriptor(destinationName, GradeEntity.class).getReadMethod().invoke(source);
                    object = new PropertyDescriptor("id", object.getClass().getSuperclass())
                            .getReadMethod().invoke(object);
                } else if (field.getType().equals(TeacherEntity.class)) {
                    Object value = new PropertyDescriptor("teacherId", source.getClass()).getReadMethod()
                            .invoke(source);
                    object = getTeacherMapper()
                            .mapToEntity(getTeacherService().findTeacherById((Long) value));
                } else if (field.getType().equals(SubjectEntity.class)) {
                    Object value = new PropertyDescriptor("subjectId", source.getClass()).getReadMethod()
                            .invoke(source);
                    object = getSubjectMapper()
                            .mapToEntity(getSubjectService().findSubjectById((Long) value));
                } else if (field.getType().equals(StudentEntity.class)) {
                    Object value = new PropertyDescriptor("studentId", source.getClass()).getReadMethod()
                            .invoke(source);
                    object = getStudentMapper()
                            .mapToEntity(getStudentService().findStudentById((Long) value));
                } else {
                    object = new PropertyDescriptor(field.getName(), source.getClass()).getReadMethod().invoke(source);
                }


                new PropertyDescriptor(field.getName(), destination.getClass()).getWriteMethod().invoke(destination, object);
            }
            return destination;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MapperException(source.getClass(), GradeEntity.class);
        }
    }
//    public GradeEntity mapToEntity(GradeEto gradeEto) {
//        GradeEntity entity = new GradeEntity();
//
//        entity.setId(gradeEto.getId());
//        entity.setVersion(gradeEto.getVersion());
//        entity.setCreateDate(gradeEto.getCreateDate());
//        entity.setUpdateDate(gradeEto.getUpdateDate());
//        entity.setComment(gradeEto.getComment());
//        entity.setGradeType(gradeEto.getGradeType());
//        entity.setGradeWeight(gradeEto.getGradeWeight().setScale(2, RoundingMode.HALF_UP));
//        entity.setValue(gradeEto.getValue());
//        entity.setGradeAssigmentDate(gradeEto.getGradeAssigmentDate());
//
//        TeacherEntity teacherEntity = getTeacherMapper()
//                .mapToEntity(getTeacherService().findTeacherById(gradeEto.getTeacherId()));
//        entity.setTeacher(teacherEntity);
//
//        SubjectEntity subjectEntity = getSubjectMapper()
//                .mapToEntity(getSubjectService().findSubjectById(gradeEto.getSubjectId()));
//        entity.setSubject(subjectEntity);
//
//        StudentEntity studentEntity = getStudentMapper()
//                .mapToEntity(getStudentService().findStudentById(gradeEto.getStudentId()));
//        entity.setStudent(studentEntity);
//
//        return entity;
//    }
//
//
//    public GradeEto mapToEto(GradeEntity entity) {
//
//        GradeEto grade = new GradeEto();
//        grade.setId(entity.getId());
//        grade.setVersion(entity.getVersion());
//        grade.setCreateDate(entity.getCreateDate());
//        grade.setUpdateDate(entity.getUpdateDate());
//        grade.setGradeType(entity.getGradeType());
//        grade.setGradeWeight(entity.getGradeWeight());
//        grade.setComment(entity.getComment());
//        grade.setValue(entity.getValue());
//        grade.setGradeAssigmentDate(entity.getGradeAssigmentDate());
//
//        if (entity.getStudent() != null) {
//            grade.setStudentId(entity.getStudent().getId());
//        }
//
//        if (entity.getSubject() != null) {
//            grade.setSubjectId(entity.getSubject().getId());
//        }
//
//        if (entity.getTeacher() != null) {
//            grade.setTeacherId(entity.getTeacher().getId());
//        }
//
//        return grade;
//    }

    public List<GradeEto> mapToETOList(List<GradeEntity> entities) {
        return entities.stream().map(entity -> this.map(entity, GradeEto.class)).collect(Collectors.toList());
    }

}



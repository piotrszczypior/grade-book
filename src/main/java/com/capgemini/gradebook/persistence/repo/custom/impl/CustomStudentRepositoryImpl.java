package com.capgemini.gradebook.persistence.repo.custom.impl;

import com.capgemini.gradebook.domain.StudentQueryEto;
import com.capgemini.gradebook.persistence.entity.StudentEntity;
import com.capgemini.gradebook.persistence.repo.custom.CustomStudentRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

public class CustomStudentRepositoryImpl implements CustomStudentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<StudentEntity> findByDateWithGradeEqualToOne(LocalDate dateOfGivingGrade) {

        TypedQuery<StudentEntity> query = entityManager.createQuery(
                "SELECT DISTINCT grade.student FROM GradeEntity grade " +
                        "WHERE grade.value = 1 AND grade.gradeAssigmentDate = :date"
                , StudentEntity.class
        );

        query.setParameter("date", dateOfGivingGrade);
        return query.getResultList();
    }

    @Override
    public Long findNumberOfStudentsWithGivenGradeOnGivenDate(StudentQueryEto studentQueryEto) {

        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT DISTINCT COUNT(grade.student) FROM GradeEntity grade " +
                        "WHERE grade.value = :value AND grade.gradeAssigmentDate = :date"
                , Long.class
        );
        query.setParameter("date", studentQueryEto.getDate());
        query.setParameter("value", studentQueryEto.getGradeValue());

        return query.getSingleResult();
    }
}

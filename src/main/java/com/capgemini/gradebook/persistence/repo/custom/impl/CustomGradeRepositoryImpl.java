package com.capgemini.gradebook.persistence.repo.custom.impl;

import com.capgemini.gradebook.domain.GradeParamsEto;
import com.capgemini.gradebook.persistence.entity.GradeEntity;
import com.capgemini.gradebook.persistence.repo.custom.CustomGradeRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomGradeRepositoryImpl implements CustomGradeRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<GradeEntity> findGamesByParams(GradeParamsEto gradeParams) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery();

        Root<GradeEntity> gradeRoot = criteriaQuery.from(GradeEntity.class);

        List<Predicate> predicates = new ArrayList<>();

        if (gradeParams.getValueFrom() != null) {
            predicates.add(
                    criteriaBuilder.greaterThanOrEqualTo(gradeRoot.get("value"), gradeParams.getValueFrom())
            );
        }

        if (gradeParams.getValueTo() != null) {
            predicates.add(
                    criteriaBuilder.lessThanOrEqualTo(gradeRoot.get("value"), gradeParams.getValueTo())
            );
        }

        if (gradeParams.getGradeWeightFrom() != null) {
            predicates.add(
                    criteriaBuilder.greaterThanOrEqualTo(gradeRoot.get("gradeWeight"), gradeParams.getGradeWeightFrom())
            );
        }

        if (gradeParams.getGradeWeightTo() != null) {
            predicates.add(
                    criteriaBuilder.lessThanOrEqualTo(gradeRoot.get("gradeWeight"), gradeParams.getGradeWeightTo())
            );
        }

        if (gradeParams.getGradeType() != null) {
            predicates.add(
                    criteriaBuilder.equal(gradeRoot.get("gradeType"), gradeParams.getGradeType())
            );
        }

        if (gradeParams.getDateOfGradeFrom() != null) {
            predicates.add(
                    criteriaBuilder.greaterThanOrEqualTo(gradeRoot.get("gradeAssigmentDate"), gradeParams.getDateOfGradeFrom())
            );
        }

        if (gradeParams.getDateOfGradeTo() != null) {
            predicates.add(
                    criteriaBuilder.lessThanOrEqualTo(gradeRoot.get("gradeAssigmentDate"), gradeParams.getDateOfGradeTo())
            );
        }

        if (gradeParams.getStudentId() != null) {
            predicates.add(
                    criteriaBuilder.equal(gradeRoot.get("student").get("id"), gradeParams.getStudentId())
            );
        }

        if (gradeParams.getSubjectId() != null) {
            predicates.add(
                    criteriaBuilder.equal(gradeRoot.get("subject").get("id"), gradeParams.getSubjectId())
            );
        }
        List<GradeEntity> foundEntities = entityManager.createQuery(
                criteriaQuery.select(gradeRoot).where(predicates.toArray((new Predicate[]{})))
        ).getResultList();

        return foundEntities;
    }
}




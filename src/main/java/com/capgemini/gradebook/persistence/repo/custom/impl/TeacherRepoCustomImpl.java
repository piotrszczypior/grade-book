package com.capgemini.gradebook.persistence.repo.custom.impl;

import com.capgemini.gradebook.persistence.entity.TeacherEntity;
import com.capgemini.gradebook.persistence.repo.custom.CustomTeacherRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class TeacherRepoCustomImpl implements CustomTeacherRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<TeacherEntity> findTeacherByLastName(String lastName) {

        List<TeacherEntity> result = entityManager.createQuery("SELECT teacher FROM TeacherEntity teacher WHERE teacher.lastName = :lastName")
                .setParameter("lastName", lastName)
                .getResultList();

        return result;
    }
}

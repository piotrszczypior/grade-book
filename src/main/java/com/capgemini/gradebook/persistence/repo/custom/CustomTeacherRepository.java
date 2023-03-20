package com.capgemini.gradebook.persistence.repo.custom;

import com.capgemini.gradebook.persistence.entity.TeacherEntity;

import java.util.List;

public interface CustomTeacherRepository {
    List<TeacherEntity> findTeacherByLastName(String lastName);
}

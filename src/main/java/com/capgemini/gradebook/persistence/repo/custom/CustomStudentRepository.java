package com.capgemini.gradebook.persistence.repo.custom;

import com.capgemini.gradebook.domain.StudentQueryEto;
import com.capgemini.gradebook.persistence.entity.StudentEntity;

import java.time.LocalDate;
import java.util.List;

public interface CustomStudentRepository {
    List<StudentEntity> findByDateWithGradeEqualToOne(LocalDate dateOfGivingGrade);
    Long findNumberOfStudentsWithGivenGradeOnGivenDate(StudentQueryEto studentQueryEto);

}

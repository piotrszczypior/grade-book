package com.capgemini.gradebook.service;

import com.capgemini.gradebook.domain.StudentEto;
import com.capgemini.gradebook.domain.StudentQueryEto;

import java.time.LocalDate;
import java.util.List;

public interface StudentQueryService {

    List<StudentEto> findStudentByDateWhoWereGivenGradeEqualToOne(LocalDate dateOfGivingGrade);

    Long findNumberOfStudentsByValueAndDate(StudentQueryEto studentQueryEto);
}

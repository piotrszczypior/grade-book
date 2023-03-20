package com.capgemini.gradebook.service.impl;

import com.capgemini.gradebook.domain.StudentEto;
import com.capgemini.gradebook.domain.StudentQueryEto;
import com.capgemini.gradebook.domain.mapper.support.AbstractMapperSupport;
import com.capgemini.gradebook.persistence.repo.StudentRepository;
import com.capgemini.gradebook.service.StudentQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class StudentQueryServiceImpl extends AbstractMapperSupport implements StudentQueryService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentQueryServiceImpl(final StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<StudentEto> findStudentByDateWhoWereGivenGradeEqualToOne(LocalDate dateOfGivingGrade) {

        return getStudentMapper().mapToETOList(studentRepository.findByDateWithGradeEqualToOne(dateOfGivingGrade));
    }

    @Override
    public Long findNumberOfStudentsByValueAndDate(StudentQueryEto studentQueryEto) {
        return studentRepository.findNumberOfStudentsWithGivenGradeOnGivenDate(studentQueryEto);
    }
}

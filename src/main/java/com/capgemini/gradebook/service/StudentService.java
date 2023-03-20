package com.capgemini.gradebook.service;

import com.capgemini.gradebook.domain.StudentEto;

import java.util.List;

public interface StudentService {

    List<StudentEto> findAllStudents();

    List<StudentEto> findStudentByLastName(final String lastname);

    StudentEto findStudentById(Long id);

    StudentEto save(StudentEto student);

    void delete(Long id);
}

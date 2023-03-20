package com.capgemini.gradebook.service.impl;

import com.capgemini.gradebook.domain.StudentEto;
import com.capgemini.gradebook.domain.mapper.support.AbstractMapperSupport;
import com.capgemini.gradebook.exception.NotFoundException;
import com.capgemini.gradebook.persistence.entity.StudentEntity;
import com.capgemini.gradebook.persistence.repo.ClassYearRepository;
import com.capgemini.gradebook.persistence.repo.StudentRepository;
import com.capgemini.gradebook.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentServiceImpl extends AbstractMapperSupport implements StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    @Override
    public List<StudentEto> findAllStudents() {
        return getStudentMapper().mapToETOList(this.studentRepository.findAll());
    }

    @Override
    public List<StudentEto> findStudentByLastName(String lastname) {
        List<StudentEntity> studentsWithGivenLastName = this.studentRepository.findAll()
                .stream().filter(student -> student.getLastName().equals(lastname))
                .collect(Collectors.toList());

        return getStudentMapper().mapToETOList(studentsWithGivenLastName);
    }

    @Override
    public StudentEto findStudentById(Long id) {

        Optional<StudentEntity> studentEntityOptional = this.studentRepository.findById(id);

        if (studentEntityOptional.isEmpty()) {
            throw new NotFoundException("No student has been found with given id = " + id);
        }

        return getStudentMapper().mapToETO(studentEntityOptional.get());
    }

    @Override
    public StudentEto save(StudentEto newStudent) {
        StudentEntity newStudentEntity = getStudentMapper().mapToEntity(newStudent);
        return getStudentMapper().mapToETO(this.studentRepository.save(newStudentEntity));
    }

    @Override
    public void delete(Long id) {
        Optional<StudentEntity> studentOptional = this.studentRepository.findById(id);

        if (studentOptional.isEmpty()) {
            throw new NotFoundException("No student with given id = " + id + " has been found.");
        }
        StudentEntity student = studentOptional.get();

        student.getGrades().forEach(grade -> grade.setStudent(null));
        student.getClassYear().getStudents().remove(student);

        this.studentRepository.deleteById(id);
    }
}

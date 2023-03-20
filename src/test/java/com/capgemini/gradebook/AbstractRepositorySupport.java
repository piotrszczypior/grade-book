package com.capgemini.gradebook;

import com.capgemini.gradebook.persistence.repo.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

public abstract class AbstractRepositorySupport {

    @Inject
    private ClassYearRepository classYearRepository;

    @Inject
    private StudentRepository studentRepository;

    @Inject
    private TeacherRepository teacherRepository;

    @Inject
    private SubjectRepository subjectRepository;

    @Inject
    private GradeRepository gradeRepository;


    public void cleanDb() {
        gradeRepository.deleteAll();
        studentRepository.deleteAll();
        subjectRepository.deleteAll();
        teacherRepository.deleteAll();
        classYearRepository.deleteAll();
    }

    public ClassYearRepository getClassYearRepository() {
        return classYearRepository;
    }

    public void setClassYearRepository(ClassYearRepository classYearRepository) {
        this.classYearRepository = classYearRepository;
    }

    public StudentRepository getStudentRepository() {
        return studentRepository;
    }

    public void setStudentRepository(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public TeacherRepository getTeacherRepository() {
        return teacherRepository;
    }

    public void setTeacherRepository(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public SubjectRepository getSubjectRepository() {
        return subjectRepository;
    }

    public void setSubjectRepository(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public GradeRepository getGradeRepository() {
        return gradeRepository;
    }

    public void setGradeRepository(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }
}

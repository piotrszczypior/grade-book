package com.capgemini.gradebook.domain.mapper.support;

import com.capgemini.gradebook.domain.mapper.*;

import javax.inject.Inject;

public abstract class AbstractMapperSupport {

    private ClassYearMapper classYearMapper;

    private GradeMapper gradeMapper;

    private StudentMapper studentMapper;

    private SubjectMapper subjectMapper;

    private TeacherMapper teacherMapper;

    public SubjectMapper getSubjectMapper() {
        return subjectMapper;
    }

    @Inject
    public void setSubjectMapper(SubjectMapper subjectMapper) {
        this.subjectMapper = subjectMapper;
    }

    public StudentMapper getStudentMapper() {
        return studentMapper;
    }

    @Inject
    public void setStudentMapper(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    public TeacherMapper getTeacherMapper() {
        return teacherMapper;
    }

    @Inject
    public void setTeacherMapper(TeacherMapper teacherMapper) {
        this.teacherMapper = teacherMapper;
    }

    public GradeMapper getGradeMapper() {
        return gradeMapper;
    }

    @Inject
    public void setGradeMapper(GradeMapper gradeMapper) {
        this.gradeMapper = gradeMapper;
    }

    public ClassYearMapper getClassYearMapper() {
        return classYearMapper;
    }

    @Inject
    public void setClassYearMapper(ClassYearMapper classYearMapper) {
        this.classYearMapper = classYearMapper;
    }
}

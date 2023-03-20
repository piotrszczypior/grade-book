package com.capgemini.gradebook.service.support;

import com.capgemini.gradebook.domain.mapper.support.AbstractMapperSupport;
import com.capgemini.gradebook.service.*;

import javax.inject.Inject;

public abstract class AbstractServiceSupport extends AbstractMapperSupport {

    private ClassYearService classYearService;

    private GradeService gradeService;

    private StudentService studentService;

    private SubjectService subjectService;

    private TeacherService teacherService;

    public ClassYearService getClassYearService() {
        return classYearService;
    }

    @Inject
    public void setClassYearService(ClassYearService classYearService) {
        this.classYearService = classYearService;
    }

    public GradeService getGradeService() {
        return gradeService;
    }

    @Inject
    public void setGradeService(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    public StudentService getStudentService() {
        return studentService;
    }

    @Inject
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    public SubjectService getSubjectService() {
        return subjectService;
    }

    @Inject
    public void setSubjectService(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    public TeacherService getTeacherService() {
        return teacherService;
    }

    @Inject
    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }
}

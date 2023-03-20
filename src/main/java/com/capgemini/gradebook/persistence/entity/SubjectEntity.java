package com.capgemini.gradebook.persistence.entity;

import com.capgemini.gradebook.persistence.entity.data.SubjectType;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "SUBJECT")
public class SubjectEntity extends AbstractEntity {

    private String name;

    @Enumerated(EnumType.STRING)
    private SubjectType subjectType;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "teacher_id")
    private TeacherEntity teacherEntity;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "classYear_id")
    private ClassYearEntity classYear;

    @OneToMany(
            mappedBy = "subject",
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST
    )
    private List<GradeEntity> grades;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SubjectType getSubjectType() {
        return this.subjectType;
    }

    public void setSubjectType(SubjectType subjectType) {
        this.subjectType = subjectType;
    }

    public TeacherEntity getTeacher() {
        return this.teacherEntity;
    }

    public void setTeacher(TeacherEntity teacherEntity) {
        this.teacherEntity = teacherEntity;
    }

    public ClassYearEntity getClassYear() {
        return this.classYear;
    }

    public void setClassYear(ClassYearEntity classYear) {
        this.classYear = classYear;
    }

    public List<GradeEntity> getGrades() {
        return this.grades;
    }

    public void setGrades(List<GradeEntity> grades) {
        this.grades = grades;
    }

//    @PreRemove
//    public void removeGradesFromStudent() {
//        classYear.getSubjects().remove(this);
//    }
}

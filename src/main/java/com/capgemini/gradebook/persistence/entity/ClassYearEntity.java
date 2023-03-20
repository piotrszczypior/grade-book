package com.capgemini.gradebook.persistence.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "CLASS_YEAR")
public class ClassYearEntity extends AbstractEntity {

    private Integer classLevel;

    private String className;

    private String classYear;

    @OneToMany(
            mappedBy = "classYear",
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST
    )
    private List<StudentEntity> students;


    @OneToMany(
            mappedBy = "classYear",
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST
    )
    private List<SubjectEntity> subjects;

    public Integer getClassLevel() {
        return this.classLevel;
    }

    public void setClassLevel(Integer classLevel) {
        this.classLevel = classLevel;
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassYear() {
        return this.classYear;
    }

    public void setClassYear(String classYear) {
        this.classYear = classYear;
    }

    public List<StudentEntity> getStudents() {
        return this.students;
    }

    public void setStudents(List<StudentEntity> students) {
        this.students = students;
    }

    public List<SubjectEntity> getSubjects() {
        return this.subjects;
    }

    public void setSubjects(List<SubjectEntity> subjects) {
        this.subjects = subjects;
    }
}

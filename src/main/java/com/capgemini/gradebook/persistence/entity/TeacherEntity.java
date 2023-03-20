package com.capgemini.gradebook.persistence.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "TEACHER")
public class TeacherEntity extends AbstractEntity {

    private String firstName;
    private String lastName;

    @OneToMany(
            mappedBy = "teacherEntity",
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST
    )
    private List<SubjectEntity> subjectEntityList;

    @OneToMany(
            mappedBy = "teacher",
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST
    )
    private List<GradeEntity> grades;

    public String getFirstName() {

        return this.firstName;
    }

    public void setFirstName(String firstName) {

        this.firstName = firstName;
    }

    public String getLastName() {

        return this.lastName;
    }

    public void setLastName(String lastName) {

        this.lastName = lastName;
    }

    public List<SubjectEntity> getSubjects() {
        return this.subjectEntityList;
    }

    public void setSubjectEntityList(List<SubjectEntity> subjectEntityList) {
        this.subjectEntityList = subjectEntityList;
    }

    public List<GradeEntity> getGrades() {
        return grades;
    }

    public void setGrades(List<GradeEntity> grades) {
        this.grades = grades;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeacherEntity teacher = (TeacherEntity) o;
        return firstName.equals(teacher.firstName) && lastName.equals(teacher.lastName) && subjectEntityList.equals(teacher.subjectEntityList) && grades.equals(teacher.grades);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, subjectEntityList, grades);
    }
}

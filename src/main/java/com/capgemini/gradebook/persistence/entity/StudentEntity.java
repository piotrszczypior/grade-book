package com.capgemini.gradebook.persistence.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "STUDENT")
public class StudentEntity extends AbstractEntity {

    private String firstName;

    private String lastName;

    private Integer age;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "classYear_id")
    private ClassYearEntity classYear;

    //when we delete student we want to delete also all his grades
    @OneToMany(
            mappedBy = "student",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE
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

    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
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
}

package com.capgemini.gradebook.persistence.entity;

import com.capgemini.gradebook.persistence.entity.data.GradeType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Table(name = "GRADE", uniqueConstraints = {@UniqueConstraint(columnNames = {"gradeAssigmentDate", "gradeType", "STUDENT_ID"})})
public class GradeEntity extends AbstractEntity {

    @Min(message = "Grade value should be between 1 and 6", value = 1)
    @Max(message = "Grade value should be between 1 and 6", value = 6)
    private Integer value;


    @Min(message = "Grade weight value should be between 1 and 9", value = 1)
    @Max(message = "Grade weight value should be between 1 and 9", value = 9)
    private BigDecimal gradeWeight;

    private GradeType gradeType;

    private String comment;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate gradeAssigmentDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private StudentEntity student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private TeacherEntity teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private SubjectEntity subject;

    public Integer getValue() {
        return this.value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public BigDecimal getGradeWeight() {
        return this.gradeWeight;
    }

    public void setGradeWeight(BigDecimal gradeWeight) {
        this.gradeWeight = gradeWeight;
    }

    public GradeType getGradeType() {
        return this.gradeType;
    }

    public void setGradeType(GradeType gradeType) {
        this.gradeType = gradeType;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getGradeAssigmentDate() {
        return this.gradeAssigmentDate;
    }

    public void setGradeAssigmentDate(LocalDate creationDate) {
        this.gradeAssigmentDate = creationDate;
    }

    public StudentEntity getStudent() {
        return this.student;
    }

    public void setStudent(StudentEntity student) {
        this.student = student;
    }

    public TeacherEntity getTeacher() {
        return this.teacher;
    }

    public void setTeacher(TeacherEntity teacher) {
        this.teacher = teacher;
    }

    public SubjectEntity getSubject() {
        return this.subject;
    }

    public void setSubject(SubjectEntity subject) {
        this.subject = subject;
    }
}

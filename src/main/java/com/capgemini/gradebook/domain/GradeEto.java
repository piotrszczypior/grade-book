package com.capgemini.gradebook.domain;

import com.capgemini.gradebook.persistence.entity.data.GradeType;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public class GradeEto extends AbstractEto {

    private Integer value;

    private BigDecimal gradeWeight;

    private GradeType gradeType;

    private String comment;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate gradeAssigmentDate;

    private Long studentId;

    private Long teacherId;

    private Long subjectId;

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

    public void setGradeAssigmentDate(LocalDate gradeAssigmentDate) {
        this.gradeAssigmentDate = gradeAssigmentDate;
    }

    public Long getStudentId() {
        return this.studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getTeacherId() {
        return this.teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Long getSubjectId() {
        return this.subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }
}

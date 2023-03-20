package com.capgemini.gradebook.domain;

import com.capgemini.gradebook.persistence.entity.data.GradeType;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class GradeParamsEto {
    private Integer valueFrom;
    private Integer valueTo;
    private Integer gradeWeightFrom;
    private Integer gradeWeightTo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfGradeFrom;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate getDateOfGradeTo;
    private GradeType gradeType;
    private Long subjectId;
    private Long studentId;

    public Integer getValueFrom() {
        return valueFrom;
    }

    public void setValueFrom(Integer valueFrom) {
        this.valueFrom = valueFrom;
    }

    public Integer getValueTo() {
        return valueTo;
    }


    public void setValueTo(Integer valueTo) {
        this.valueTo = valueTo;
    }

    public Integer getGradeWeightFrom() {
        return gradeWeightFrom;
    }

    public void setGradeWeightFrom(Integer gradeWeightFrom) {
        this.gradeWeightFrom = gradeWeightFrom;
    }

    public Integer getGradeWeightTo() {
        return gradeWeightTo;
    }

    public void setGradeWeightTo(Integer gradeWeightTo) {
        this.gradeWeightTo = gradeWeightTo;
    }

    public LocalDate getDateOfGradeFrom() {
        return dateOfGradeFrom;
    }

    public void setDateOfGradeFrom(LocalDate dateOfGradeFrom) {
        this.dateOfGradeFrom = dateOfGradeFrom;
    }

    public LocalDate getDateOfGradeTo() {
        return getDateOfGradeTo;
    }

    public void setGetDateOfGradeTo(LocalDate getDateOfGradeTo) {
        this.getDateOfGradeTo = getDateOfGradeTo;
    }

    public GradeType getGradeType() {
        return gradeType;
    }

    public void setGradeType(GradeType gradeType) {
        this.gradeType = gradeType;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}

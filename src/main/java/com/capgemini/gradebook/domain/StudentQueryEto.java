package com.capgemini.gradebook.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class StudentQueryEto {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private Integer gradeValue;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getGradeValue() {
        return gradeValue;
    }

    public void setGradeValue(Integer gradeValue) {
        this.gradeValue = gradeValue;
    }
}

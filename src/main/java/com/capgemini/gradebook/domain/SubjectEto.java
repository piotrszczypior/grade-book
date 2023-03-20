package com.capgemini.gradebook.domain;

import com.capgemini.gradebook.persistence.entity.data.SubjectType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class SubjectEto extends AbstractEto {

    @Enumerated(EnumType.STRING)
    private SubjectType subjectType;

    private Long teacherId;

    private Long classYearId;

    private String subjectName;

    public SubjectType getSubjectType() {
        return this.subjectType;
    }

    public void setSubjectType(SubjectType subjectType) {
        this.subjectType = subjectType;
    }

    public Long getTeacherId() {
        return this.teacherId;
    }

    public void setTeacherId(Long teacherEntityId) {
        this.teacherId = teacherEntityId;
    }

    public Long getClassYearId() {
        return this.classYearId;
    }

    public void setClassYearId(Long classYearId) {
        this.classYearId = classYearId;
    }

    public String getSubjectName() {
        return this.subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}

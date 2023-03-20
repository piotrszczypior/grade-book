package com.capgemini.gradebook.domain;

public class ClassYearEto extends AbstractEto {
    private Integer classLevel;

    private String className;

    private String classYear;

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
}

package com.capgemini.gradebook.domain;

public class StudentEto extends AbstractEto {

    private String firstName;

    private String lastName;

    private Integer age;

    private Long classYearId;

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

    public Long getClassYearId() {
        return this.classYearId;
    }

    public void setClassYearId(Long classYearId) {
        this.classYearId = classYearId;
    }


}

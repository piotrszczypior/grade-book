package com.capgemini.gradebook.domain;

public class TeacherEto extends AbstractEto {

    private String firstName;
    private String lastName;
    //dev note: remember, if Entity contains a list of other entities, Eto class should contain a list of
    // Ids, or even nothing that represents a one-to-many relationship, to avoid circular, never-ending mapping in mappers.

    /**
     * @return firstName
     */
    public String getFirstName() {

        return this.firstName;
    }

    /**
     * @param firstName der neue Wert.
     */
    public void setFirstName(String firstName) {

        this.firstName = firstName;
    }

    /**
     * @return lastName
     */
    public String getLastName() {

        return this.lastName;
    }

    /**
     * @param lastName der neue Wert.
     */
    public void setLastName(String lastName) {

        this.lastName = lastName;
    }
}

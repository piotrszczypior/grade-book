package com.capgemini.gradebook.service;

import com.capgemini.gradebook.AbstractRepositorySupport;
import com.capgemini.gradebook.domain.StudentEto;
import com.capgemini.gradebook.persistence.entity.ClassYearEntity;
import com.capgemini.gradebook.persistence.entity.StudentEntity;
import com.capgemini.gradebook.persistence.repo.ClassYearRepository;
import com.capgemini.gradebook.persistence.repo.StudentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import java.util.List;

@SpringBootTest
class StudentServiceTest extends AbstractRepositorySupport {

    @Inject
    private StudentService testedClass;


    @BeforeEach
    @AfterEach
    @Override
    public void cleanDb() {
        super.cleanDb();
    }

    @Test
    void findAllStudents_ShouldNotBeEmpty() {
        // given
        saveTestData();

        //When
        List<StudentEto> result = this.testedClass.findAllStudents();

        //then
        Assertions.assertThat(result).isNotEmpty();
    }

    @Test
    void findStudentByLastName_ShouldNotReturnNull() {
        //given
        StudentEntity savedStudent = saveTestData();

        //when
        List<StudentEto> result = this.testedClass.findStudentByLastName(
                savedStudent.getLastName()
        );

        //then
        Assertions.assertThat(result).isNotEmpty();
    }

    @Test
    void findStudentById_ShouldNotBeNull() {
        //given
        StudentEntity savedStudent = saveTestData();

        //When
        StudentEto result = this.testedClass.findStudentById(savedStudent.getId());

        //then
        Assertions.assertThat(result).isNotNull();
    }

    @Test
    @Transactional
    void save_ShouldCreateObjectInDB() {
        //given
        StudentEto studentToSave = createStudentDataToSave();

//        ClassYearEntity classYear = new ClassYearEntity();
//        classYear.setClassName("1a");
//        ClassYearEntity savedClass = getClassYearRepository().save(classYear);
//
//        var a = new ClassYearEntity();
//        a.setId(savedClass.getId());
//        StudentEntity student = new StudentEntity();
//        student.setClassYear(a);
//        getStudentRepository().save(student);
//
//        //When
        this.testedClass.save(studentToSave);
        List<StudentEntity> students = getStudentRepository().findAll();
//
//        //then
        Assertions.assertThat(students).isNotEmpty();
        Assertions.assertThat(students.get(0).getClassYear().getId()).isEqualTo(studentToSave.getClassYearId());
//        Assertions.assertThat(students.get(0).getClassYear().getClassName()).isEqualTo("1a");
    }

    @Test
    void delete_ResultShouldBeEmpty() {
        //given
        StudentEntity studentToDelete = saveTestData();

        //when
        this.testedClass.delete(studentToDelete.getId());
        List<StudentEntity> result = getStudentRepository().findAll();

        //then
        Assertions.assertThat(result).isEmpty();
    }

    private StudentEntity saveTestData() {
        ClassYearEntity classYear = new ClassYearEntity();
        getClassYearRepository().save(classYear);

        StudentEntity student = new StudentEntity();
        student.setClassYear(classYear);
        student.setLastName("Smith");
        getStudentRepository().save(student);

        return student;
    }

    private StudentEto createStudentDataToSave() {
        ClassYearEntity classYear = new ClassYearEntity();
        var savedClassYear = getClassYearRepository().save(classYear);

        StudentEto student = new StudentEto();
        student.setClassYearId(savedClassYear.getId());

        return student;
    }

}
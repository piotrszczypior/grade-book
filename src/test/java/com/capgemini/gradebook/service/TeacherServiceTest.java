package com.capgemini.gradebook.service;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import com.capgemini.gradebook.AbstractRepositorySupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.capgemini.gradebook.domain.TeacherEto;
import com.capgemini.gradebook.persistence.entity.TeacherEntity;
import com.capgemini.gradebook.persistence.repo.TeacherRepository;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TeacherServiceTest extends AbstractRepositorySupport {

    @Inject
    private TeacherService testedClass;


    @BeforeEach
    @AfterEach
    @Override
    public void cleanDb() {
        super.cleanDb();
    }

    /**
     * Integration test that persists some test data and checks, if after findAllTeachers the populated list will not
     * be empty
     */
    @Test
    public void findAllTeachers_ShouldNotBeEmptyAfterInsert() {

        // given
        saveTestData();

        // when
        List<TeacherEto> result = this.testedClass.findAllTeachers();

        // then
        assertThat(result).isNotEmpty();
    }

    @Test
    public void findTeacherById_ShouldNotBeEmpty() {
        // given
        TeacherEntity savedTeacher = saveTestData();

        //When
        TeacherEto result = this.testedClass.findTeacherById(savedTeacher.getId());

        //Then
        assertThat(result).isNotNull();
        assertThat(result.getFirstName()).isEqualTo(savedTeacher.getFirstName());
        assertThat(result.getLastName()).isEqualTo(savedTeacher.getLastName());
    }

    @Test
    public void findTeacherByName_ShouldNotBeEmpty() {
        //Given
        TeacherEntity savedEntity = saveTestData();

        //When
        List<TeacherEto> result = this.testedClass.findTeacherByLastName(
                savedEntity.getLastName()
        );

        //Then
        Assertions.assertThat(result).isNotEmpty();
        assertThat(result.size()).isOne();
        TeacherEto teacherEto = result.stream().findAny().get();
        assertThat(
                teacherEto.getFirstName()).isEqualTo(savedEntity.getFirstName()
        );

        assertThat(
                teacherEto.getLastName()).isEqualTo(savedEntity.getLastName()
        );
    }

    @Test
    public void saveTeacher_ShouldSaveTeacherObject() {
        //Given
        TeacherEto teacherToSave = new TeacherEto();

        //When
        Long createdEntityId = this.testedClass.save(teacherToSave).getId();
        Optional<TeacherEntity> createdTeacher =  getTeacherRepository().findById(createdEntityId);

        //Then
        assertThat(createdTeacher).isNotEmpty();
        assertThat(createdTeacher.get().getFirstName()).isEqualTo(teacherToSave.getFirstName());
        assertThat(createdTeacher.get().getLastName()).isEqualTo(teacherToSave.getLastName());
    }

    @Test
    public void deleteTeacher_ResultShouldBeEmpty() {
        // Given
        TeacherEntity teacherToDelete = saveTestData();

        //when
        this.testedClass.delete(teacherToDelete.getId());
        List<TeacherEntity> result = getTeacherRepository().findAll();

        //Then
        Assertions.assertThat(result).isEmpty();
    }


    private TeacherEntity saveTestData() {
        TeacherEntity entity = new TeacherEntity();
        entity.setFirstName("Jan");
        entity.setLastName("Kowalski");
        return getTeacherRepository().save(entity);
    }

}
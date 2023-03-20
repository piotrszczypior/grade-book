package com.capgemini.gradebook.service;

import com.capgemini.gradebook.AbstractRepositorySupport;
import com.capgemini.gradebook.domain.ClassYearEto;
import com.capgemini.gradebook.persistence.entity.ClassYearEntity;
import com.capgemini.gradebook.persistence.repo.ClassYearRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import javax.inject.Inject;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class ClassYearServiceTest extends AbstractRepositorySupport {

    @Inject
    private ClassYearService testedClass;

    @AfterEach
    @Override
    public void cleanDb() {
        super.cleanDb();
    }

    @Test
    void findAllClasses_ShouldNotBeEmpty() {
        //given
        createTestData();

        //when
        List<ClassYearEto> result = this.testedClass.findAllClasses();

        //then
        Assertions.assertThat(result).isNotEmpty();
    }

    @Test
    void findClassYearByClassYear_ShouldNotBeEmpty() {
        //given
        ClassYearEntity savedClass = createTestData();

        //when
        List<ClassYearEto> result = this.testedClass.findClassYearByClassLevel(savedClass.getClassLevel());

        //Then
        Assertions.assertThat(result).isNotEmpty();
        ClassYearEto testedClassYear = result.stream().findFirst().get();

        Assertions.assertThat(testedClassYear.getClassYear())
                .isEqualTo(savedClass.getClassYear());

        Assertions.assertThat(testedClassYear.getClassName())
                .isEqualTo(savedClass.getClassName());

        Assertions.assertThat(testedClassYear.getClassLevel())
                .isEqualTo(savedClass.getClassLevel());
    }

    @Test
    void findClassYearById_ShouldNotBeNull() {
        //given
        ClassYearEntity savedClass = createTestData();

        //when
        ClassYearEto result = this.testedClass.findClassYearById(savedClass.getId());

        //then
        Assertions.assertThat(result).isNotNull();

        Assertions.assertThat(result.getClassYear())
                .isEqualTo(savedClass.getClassYear());

        Assertions.assertThat(result.getClassName())
                .isEqualTo(savedClass.getClassName());

        Assertions.assertThat(result.getClassLevel())
                .isEqualTo(savedClass.getClassLevel());
    }

    @Test
    void save_ShouldBeNotEmpty() {
        //given
        ClassYearEto givenClassYear = new ClassYearEto();
        givenClassYear.setClassName("a");
        givenClassYear.setClassYear("21/22");
        givenClassYear.setClassLevel(2);

        //when
        ClassYearEto createdClass = this.testedClass.save(givenClassYear);
        Optional<ClassYearEntity> result = getClassYearRepository().findById(createdClass.getId());

        //then
        Assertions.assertThat(result).isNotNull();

        Assertions.assertThat(result.get().getClassYear())
                .isEqualTo(givenClassYear.getClassYear());

        Assertions.assertThat(result.get().getClassName())
                .isEqualTo(givenClassYear.getClassName());

        Assertions.assertThat(result.get().getClassLevel())
                .isEqualTo(givenClassYear.getClassLevel());

    }

    @Test
    void delete_ShouldBeEmpty() {
        //given
        ClassYearEntity savedClass = createTestData();

        //when
        this.testedClass.delete(savedClass.getId());
        Optional<ClassYearEntity> result = getClassYearRepository().findById(savedClass.getId());

        //then
        Assertions.assertThat(result).isNotPresent();
    }

    private ClassYearEntity createTestData() {
        ClassYearEntity classYear = new ClassYearEntity();
        classYear.setClassName("a");
        classYear.setClassYear("21/22");
        classYear.setClassLevel(2);

        return getClassYearRepository().save(classYear);
    }
}
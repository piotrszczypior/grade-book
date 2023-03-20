package com.capgemini.gradebook.features;

import com.capgemini.gradebook.AbstractRepositorySupport;
import com.capgemini.gradebook.domain.GradeEto;
import com.capgemini.gradebook.exception.NoArgumentException;
import com.capgemini.gradebook.persistence.entity.*;
import com.capgemini.gradebook.persistence.entity.data.GradeType;
import com.capgemini.gradebook.service.impl.GradeServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GradeServiceImplTest extends AbstractRepositorySupport {

    @Inject
    private GradeServiceImpl testedClass;

    @BeforeEach
    @AfterEach
    @Override
    public void cleanDb() {
        super.cleanDb();
    }

    @Test
    void gradeValueShouldBeBetween1and6_ShouldThrowConstraintViolationException() {
        //given
        GradeEntity gradeToTest = createTestDataBadGradeValue();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        //when
        Set<ConstraintViolation<GradeEntity>> constraintViolations =
                validator.validate(gradeToTest);

        //then
        assertThat(constraintViolations.size()).isZero();
    }

    @Test
    void noComment_ShouldThrowNoArgumentException() {
        //given
        GradeEto gradeToSave = createTestDataNoComment();

        //when
        NoArgumentException result = assertThrows(
                NoArgumentException.class,
                () -> this.testedClass.save(gradeToSave)
        );

        //then
        assertEquals("Comment is required when grade is 1 or 6", result.getMessage());
        assertTrue(getGradeRepository().findAll().isEmpty());
    }

    @Test
    void defaultValueOfGradeWeight_GradeWeightShouldBeOne() {
        //given
        GradeEto gradeToSave = createTestDataDefaultGradeWeightValue();
        BigDecimal expectedWeight = new BigDecimal("1.00");

        //when
        GradeEto result = this.testedClass.save(gradeToSave);

        //then
        assertNotNull(result.getGradeWeight());
        assertThat(result.getGradeWeight()).isEqualTo(expectedWeight);
        assertEquals(result.getGradeWeight(), getGradeRepository().findById(result.getId()).get().getGradeWeight());
    }

    @Test
    @DisplayName("Test - save grade with gradeType as Enum into DB")
    void gradeTypeTest_ShouldNotBeNull() {
        //given
        GradeEto gradeToSave = createTestDataGradeType();

        //when
        GradeEto result = this.testedClass.save(gradeToSave);

        //then
        assertNotNull(result);
        assertEquals(result.getGradeType(), gradeToSave.getGradeType());
        assertEquals(result.getGradeType(), getGradeRepository().findById(result.getId()).get().getGradeType());
    }

    @Test
    @DisplayName("Test - save grade with gradeType as Enum into DB")
    void gradeTypeTest_gradeTypeIsNull_ShouldThrowNoArgumentException() {
        //given
        GradeEto gradeToSave = createTestDataWithoutGradeType();

        //when
        NoArgumentException result = assertThrows(
                NoArgumentException.class,
                () -> this.testedClass.save(gradeToSave)
        );

        //then
        assertThat(result.getMessage()).isEqualTo("Grade should have type!");
    }

    @Test
    void featureTest_oneStudentShouldBeGivenOnlyOneMarkInParticularGradeTypeEveryDay_ShouldThrowGradeDateException() {
        //given
        GradeEntity gradeEntity = createTestDataGradeDateForException();

        //when
        Executable result = () -> getGradeRepository().save(gradeEntity);

        //then
        assertThrows(DataIntegrityViolationException.class, result);
    }


    @Test
    @DisplayName("Test - students get only one mark in particular grade type everyday")
    void oneStudentShouldBeGivenOnlyOneMarkInParticularGradeTypeEveryDay_ShouldNotBeNull() {
        //given
        GradeEto gradeToSave = createTestDataGradeDate();

        //when
        GradeEto result = this.testedClass.save(gradeToSave);

        //then
        assertNotNull(result);
        assertEquals(result.getGradeType(), gradeToSave.getGradeType());
    }

    private GradeEto getGradeEto() {
        ClassYearEntity classYear = new ClassYearEntity();
        classYear = getClassYearRepository().save(classYear);

        TeacherEntity teacher = new TeacherEntity();
        teacher = getTeacherRepository().save(teacher);

        StudentEntity student = new StudentEntity();
        student.setClassYear(classYear);
        student = getStudentRepository().save(student);

        SubjectEntity subject = new SubjectEntity();
        subject.setTeacher(teacher);
        subject.setClassYear(classYear);
        subject = getSubjectRepository().save(subject);

        GradeEto grade = new GradeEto();
        grade.setSubjectId(subject.getId());
        grade.setTeacherId(teacher.getId());
        grade.setStudentId(student.getId());
        return grade;
    }

    private GradeEntity createTestDataBadGradeValue() {
        TeacherEntity teacher = new TeacherEntity();
        teacher = getTeacherRepository().save(teacher);

        StudentEntity student = new StudentEntity();
        student = getStudentRepository().save(student);

        SubjectEntity subject = new SubjectEntity();
        subject = getSubjectRepository().save(subject);

        GradeEntity grade = new GradeEntity();
        grade.setSubject(subject);
        grade.setTeacher(teacher);
        grade.setStudent(student);

        grade.setValue(5);
        grade.setGradeWeight(new BigDecimal("4.767"));
        grade.setComment("Excellent work");
        grade.setGradeType(GradeType.TEST);
        grade.setGradeAssigmentDate(LocalDate.parse("2022-11-29"));

        return grade;
    }


    private GradeEto createTestDataNoComment() {
        GradeEto grade = getGradeEto();

        grade.setValue(6);
        grade.setGradeWeight(new BigDecimal("4.767"));
        grade.setGradeType(GradeType.TEST);
        grade.setGradeAssigmentDate(LocalDate.parse("2022-11-29"));

        return grade;
    }

    private GradeEto createTestDataDefaultGradeWeightValue() {
        GradeEto grade = getGradeEto();

        grade.setValue(6);
        grade.setComment("Excellent work");
        grade.setGradeType(GradeType.TEST);
        grade.setGradeAssigmentDate(LocalDate.parse("2022-11-29"));

        return grade;
    }

    private GradeEto createTestDataGradeType() {
        GradeEto grade = getGradeEto();

        grade.setValue(6);
        grade.setComment("Excellent work");
        grade.setGradeType(GradeType.TEST);
        grade.setGradeAssigmentDate(LocalDate.parse("2022-11-29"));

        return grade;
    }

    private GradeEto createTestDataWithoutGradeType() {
        GradeEto grade = getGradeEto();

        grade.setValue(6);
        grade.setComment("Excellent work");
        grade.setGradeAssigmentDate(LocalDate.parse("2022-11-29"));

        return grade;
    }

    private GradeEto createTestDataGradeDate() {
        saveGradeEntity();

        GradeEto grade = getGradeEto();

        grade.setValue(6);
        grade.setComment("Excellent work");
        grade.setGradeType(GradeType.EXAM);
        grade.setGradeAssigmentDate(LocalDate.parse("2022-11-29"));

        return grade;
    }

    private void saveGradeEntity() {
        TeacherEntity teacher = new TeacherEntity();
        teacher = getTeacherRepository().save(teacher);
        StudentEntity student = new StudentEntity();
        student = getStudentRepository().save(student);
        SubjectEntity subject = new SubjectEntity();
        subject = getSubjectRepository().save(subject);

        GradeEntity grade = new GradeEntity();

        grade.setSubject(subject);
        grade.setTeacher(teacher);
        grade.setStudent(student);

        grade.setGradeType(GradeType.EXAM);
        grade.setValue(6);
        grade.setComment("Excellent work");
        grade.setGradeType(GradeType.TEST);
        grade.setGradeAssigmentDate(LocalDate.parse("2022-11-29"));

        getGradeRepository().save(grade);
    }

    private GradeEntity createTestDataGradeDateForException() {
        TeacherEntity teacher = new TeacherEntity();
        teacher = getTeacherRepository().save(teacher);
        StudentEntity student = new StudentEntity();
        student = getStudentRepository().save(student);
        SubjectEntity subject = new SubjectEntity();
        subject = getSubjectRepository().save(subject);

        GradeEntity grade = new GradeEntity();

        grade.setSubject(subject);
        grade.setTeacher(teacher);
        grade.setStudent(student);

        grade.setValue(6);
        grade.setComment("Excellent work");
        grade.setGradeType(GradeType.TEST);
        grade.setGradeAssigmentDate(LocalDate.parse("2022-11-29"));


        getGradeRepository().save(grade);


        GradeEntity gradeEntity = new GradeEntity();
        gradeEntity.setSubject(subject);
        gradeEntity.setTeacher(teacher);
        gradeEntity.setStudent(student);

        gradeEntity.setGradeType(GradeType.TEST);
        gradeEntity.setValue(6);
        gradeEntity.setComment("Excellent");
        gradeEntity.setGradeAssigmentDate(LocalDate.parse("2022-11-29"));

        return gradeEntity;
    }
}
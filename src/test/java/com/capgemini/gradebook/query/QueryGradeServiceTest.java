package com.capgemini.gradebook.query;

import com.capgemini.gradebook.AbstractRepositorySupport;
import com.capgemini.gradebook.domain.GradeEto;
import com.capgemini.gradebook.domain.GradeParamsEto;
import com.capgemini.gradebook.persistence.entity.*;
import com.capgemini.gradebook.persistence.entity.data.GradeType;
import com.capgemini.gradebook.persistence.entity.data.SubjectType;
import com.capgemini.gradebook.service.impl.QueryGradeServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.inject.Inject;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QueryGradeServiceTest extends AbstractRepositorySupport {

    @Inject
    private QueryGradeServiceImpl testedClass;

    @BeforeEach
    @AfterEach
    @Override
    public void cleanDb() {
        super.cleanDb();
    }

    @Test
    @DisplayName("Test - grades' value between 2 and 5")
    void testFindGradesByParams_ShouldReturnGradesWithValueBetween2And5() {
        //given
        ClassYearEntity classYear = getClassYearEntity();
        TeacherEntity teacher = getTeacher();
        SubjectEntity subject = getSubjectEntity(classYear, teacher);
        StudentEntity student = getStudent(classYear);
        List<GradeEntity> createdGrades = createGrades(student, subject, teacher);

        GradeParamsEto gradeParamsEto = new GradeParamsEto();
        gradeParamsEto.setStudentId(student.getId());
        gradeParamsEto.setSubjectId(subject.getId());
        gradeParamsEto.setValueFrom(2);
        gradeParamsEto.setValueTo(5);

        //when
        List<GradeEto> result = this.testedClass.findGradesByParams(gradeParamsEto);

        //then
        assertFalse(result.isEmpty());
        assertThat(result.stream().findFirst().get()).isNotNull();
        assertThat(result.stream().map(GradeEto::getValue)).doesNotContain(6,1);
    }


    @Test
    @DisplayName("Test - grades' weight between 2 and 5")
    void testFindGradesByParams_ShouldReturnGradesWithWeightBetween2And5() {
        //given
        ClassYearEntity classYear = getClassYearEntity();
        TeacherEntity teacher = getTeacher();
        SubjectEntity subject = getSubjectEntity(classYear, teacher);
        StudentEntity student = getStudent(classYear);
        List<GradeEntity> createdGrades = createGrades(student, subject, teacher);

        GradeParamsEto gradeParamsEto = new GradeParamsEto();
        gradeParamsEto.setStudentId(student.getId());
        gradeParamsEto.setSubjectId(subject.getId());
        gradeParamsEto.setGradeWeightTo(5);
        gradeParamsEto.setGradeWeightFrom(2);

        //when
        List<GradeEto> result = this.testedClass.findGradesByParams(gradeParamsEto);

        //then
        assertFalse(result.isEmpty());
        assertThat(result.stream().findFirst().get()).isNotNull();
        assertThat(result.stream().map(GradeEto::getGradeWeight))
                .doesNotContain(
                        new BigDecimal(1), new BigDecimal(7),new BigDecimal(8),new BigDecimal(9)
                );
    }

    @Test
    @DisplayName("Test - dateOfGiving grades between 2022-03-23 and 2022-03-25")
    void testFindGradesByParams_ShouldReturnGradesWithGradeOfGivingDateBetweenDates() {
        //given
        ClassYearEntity classYear = getClassYearEntity();
        TeacherEntity teacher = getTeacher();
        SubjectEntity subject = getSubjectEntity(classYear, teacher);
        StudentEntity student = getStudent(classYear);
        List<GradeEntity> createdGrades = createGrades(student, subject, teacher);

        GradeParamsEto gradeParamsEto = new GradeParamsEto();
        gradeParamsEto.setStudentId(student.getId());
        gradeParamsEto.setSubjectId(subject.getId());
        gradeParamsEto.setDateOfGradeFrom(LocalDate.parse("2022-03-23"));
        gradeParamsEto.setGetDateOfGradeTo(LocalDate.parse("2022-03-25"));

        //when
        List<GradeEto> result = this.testedClass.findGradesByParams(gradeParamsEto);

        //then
        assertFalse(result.isEmpty());
        assertThat(result.stream().findFirst().get()).isNotNull();
        assertThat(result.stream().map(GradeEto::getGradeAssigmentDate))
                .containsAnyOf(
                        LocalDate.parse("2022-03-23"), LocalDate.parse("2022-03-24"), LocalDate.parse("2022-03-25")
                );
    }

    @Test
    @DisplayName("Test - grades that were given for an EXAM")
    void testFindGradesByParams_ShouldReturnGradesWithGivenGradeType() {
        //given
        ClassYearEntity classYear = getClassYearEntity();
        TeacherEntity teacher = getTeacher();
        SubjectEntity subject = getSubjectEntity(classYear, teacher);
        StudentEntity student = getStudent(classYear);
        List<GradeEntity> createdGrades = createGrades(student, subject, teacher);

        GradeParamsEto gradeParamsEto = new GradeParamsEto();
        gradeParamsEto.setStudentId(student.getId());
        gradeParamsEto.setSubjectId(subject.getId());
        gradeParamsEto.setGradeType(GradeType.EXAM);

        //when
        List<GradeEto> result = this.testedClass.findGradesByParams(gradeParamsEto);

        //then
        assertFalse(result.isEmpty());
        assertThat(result.stream().findFirst().get()).isNotNull();
        assertThat(result.stream().map(GradeEto::getGradeType)).containsOnly(GradeType.EXAM);
    }

    @Test
    @DisplayName("Test - grades that were given for an EXAM")
    void testFindGradesByParams_ShouldReturnEmptyList() {
        //given
        ClassYearEntity classYear = getClassYearEntity();
        TeacherEntity teacher = getTeacher();
        SubjectEntity subject = getSubjectEntity(classYear, teacher);
        StudentEntity student = getStudent(classYear);
        createGrades(student, subject, teacher);

        GradeParamsEto gradeParamsEto = new GradeParamsEto();
        gradeParamsEto.setGradeType(GradeType.PRESENTATION);

        //when
        List<GradeEto> result = this.testedClass.findGradesByParams(gradeParamsEto);

        //then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Test - return all grades saved in DB")
    void testFindGradesByParam_ShouldReturnAllSavedGradesInDB() {
        //given
        ClassYearEntity classYear = getClassYearEntity();
        TeacherEntity teacher = getTeacher();
        SubjectEntity subject = getSubjectEntity(classYear, teacher);
        StudentEntity student = getStudent(classYear);
        List<GradeEntity> grades = createGrades(student, subject, teacher);

        GradeParamsEto gradeParamsEto = new GradeParamsEto();

        //when
        List<GradeEto> result = this.testedClass.findGradesByParams(gradeParamsEto);


        //then
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(grades.size());

    }


    private StudentEntity getStudent(ClassYearEntity classYear) {
        StudentEntity student = new StudentEntity();
        student.setClassYear(classYear);
        student.setAge(20);
        student.setLastName("Mojito");
        student.setFirstName("John");

        return getStudentRepository().save(student);
    }

    private ClassYearEntity getClassYearEntity() {
        ClassYearEntity classYear = new ClassYearEntity();
        classYear.setClassLevel(2);
        classYear.setClassName("a");
        return getClassYearRepository().save(classYear);
    }

    private SubjectEntity getSubjectEntity(ClassYearEntity classYear, TeacherEntity teacher) {
        SubjectEntity subjectEntity = new SubjectEntity();
        subjectEntity.setSubjectType(SubjectType.BIOLOGY);
        subjectEntity.setClassYear(classYear);
        subjectEntity.setTeacher(teacher);
        subjectEntity.setName(
                subjectEntity.getSubjectType() + "_" +
                        subjectEntity.getClassYear().getClassLevel() +
                        subjectEntity.getClassYear().getClassName()
        );

        return getSubjectRepository().save(subjectEntity);
    }

    private TeacherEntity getTeacher() {
        TeacherEntity teacher = new TeacherEntity();
        teacher.setFirstName("Elizabeth");
        teacher.setLastName("Smith");
        teacher = getTeacherRepository().save(teacher);
        return teacher;
    }

    private List<GradeEntity> createGrades(StudentEntity savedStudent, SubjectEntity subjectEntity, TeacherEntity teacher) {
        GradeEntity firstGrade = new GradeEntity();

        firstGrade.setGradeAssigmentDate(LocalDate.parse("2022-03-23"));
        firstGrade.setGradeWeight(new BigDecimal(5));
        firstGrade.setGradeType(GradeType.TEST);
        firstGrade.setValue(4);
        firstGrade.setStudent(savedStudent);
        firstGrade.setSubject(subjectEntity);
        firstGrade.setTeacher(teacher);
        getGradeRepository().save(firstGrade);

        GradeEntity secondGrade = new GradeEntity();

        secondGrade.setGradeAssigmentDate(LocalDate.parse("2022-03-26"));
        secondGrade.setGradeWeight(new BigDecimal(2));
        secondGrade.setValue(6);
        secondGrade.setGradeType(GradeType.EXAM);
        secondGrade.setStudent(savedStudent);
        secondGrade.setSubject(subjectEntity);
        secondGrade.setTeacher(teacher);
        getGradeRepository().save(secondGrade);

        GradeEntity thirdGrade = new GradeEntity();

        thirdGrade.setGradeAssigmentDate(LocalDate.parse("2022-03-28"));
        thirdGrade.setGradeWeight(new BigDecimal(7));
        thirdGrade.setValue(2);
        thirdGrade.setStudent(savedStudent);
        thirdGrade.setGradeType(GradeType.EXAM);
        thirdGrade.setSubject(subjectEntity);
        thirdGrade.setTeacher(teacher);
        getGradeRepository().save(thirdGrade);

        return List.of(firstGrade, secondGrade, thirdGrade);
    }
}
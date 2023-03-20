package com.capgemini.gradebook.query;

import com.capgemini.gradebook.AbstractRepositorySupport;
import com.capgemini.gradebook.domain.StudentEto;
import com.capgemini.gradebook.domain.StudentQueryEto;
import com.capgemini.gradebook.persistence.entity.*;
import com.capgemini.gradebook.persistence.entity.data.GradeType;
import com.capgemini.gradebook.persistence.entity.data.SubjectType;
import com.capgemini.gradebook.service.impl.StudentQueryServiceImpl;
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

@SpringBootTest
class StudentQueryServiceTest extends AbstractRepositorySupport {


    @Inject
    private StudentQueryServiceImpl testedClass;

    @BeforeEach
    @AfterEach
    @Override
    public void cleanDb() {
        super.cleanDb();
    }

    @Test
    @DisplayName("Test - students who got grade 1 on 2022-03-23")
    void findStudentByDate_ShouldReturnStudentsWhoGotGradeEqualToOneOnGivenDate() {
        //given
        var savedStudents = createTestData();
        LocalDate dateParam = LocalDate.parse("2022-03-23");

        //when
        List<StudentEto> result = this.testedClass.findStudentByDateWhoWereGivenGradeEqualToOne(dateParam);
        //then
        assertThat(result).isNotEmpty();
        assertThat(result.stream().findAny().get()).isNotNull();
    }

    @Test
    @DisplayName("Test - students who got X grade on X day")
    void findNumberOfStudentsByValueAndDate_ShouldReturnPositiveNumber() {
        //given
        var savedStudents = createTestData();
        StudentQueryEto studentQueryEto = new StudentQueryEto();
        studentQueryEto.setDate(LocalDate.parse("2022-03-26"));
        studentQueryEto.setGradeValue(6);

        //when
        Long result = this.testedClass.findNumberOfStudentsByValueAndDate(studentQueryEto);

        //then
        assertThat(result).isNotNull();
        assertThat(result).isNotZero();
        assertThat(result).isEqualTo(savedStudents.size());
    }

    private List<StudentEntity> createTestData() {
        ClassYearEntity classYear = new ClassYearEntity();
        classYear = getClassYearRepository().save(classYear);

        TeacherEntity teacher = getTeacher();

        SubjectEntity subject = getSubjectEntity(classYear, teacher);

        var firstStudent = getStudent(classYear);
        var secondStudent = getStudent(classYear);
        var thirdStudent = getStudent(classYear);

        createGrades(firstStudent, subject, teacher);
        createGrades(secondStudent, subject, teacher);
        createGrades(thirdStudent, subject, teacher);

        return List.of(firstStudent, secondStudent, thirdStudent);
    }

    private StudentEntity getStudent(ClassYearEntity classYear) {
        StudentEntity student = new StudentEntity();
        student.setClassYear(classYear);
        student.setAge(20);
        student.setLastName("Mojito");
        student.setFirstName("John");

        return getStudentRepository().save(student);
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

    private void createGrades(StudentEntity student, SubjectEntity subjectEntity, TeacherEntity teacher) {
        GradeEntity firstGrade = new GradeEntity();

        firstGrade.setGradeAssigmentDate(LocalDate.parse("2022-03-23"));
        firstGrade.setGradeWeight(new BigDecimal(5));
        firstGrade.setGradeType(GradeType.TEST);
        firstGrade.setValue(1);
        firstGrade.setStudent(student);
        firstGrade.setSubject(subjectEntity);
        firstGrade.setTeacher(teacher);
        getGradeRepository().save(firstGrade);

        GradeEntity secondGrade = new GradeEntity();

        secondGrade.setGradeAssigmentDate(LocalDate.parse("2022-03-26"));
        secondGrade.setGradeWeight(new BigDecimal(2));
        secondGrade.setValue(6);
        secondGrade.setGradeType(GradeType.EXAM);
        secondGrade.setStudent(student);
        secondGrade.setSubject(subjectEntity);
        secondGrade.setTeacher(teacher);
        getGradeRepository().save(secondGrade);

        GradeEntity thirdGrade = new GradeEntity();

        thirdGrade.setGradeAssigmentDate(LocalDate.parse("2022-03-28"));
        thirdGrade.setGradeWeight(new BigDecimal(7));
        thirdGrade.setValue(2);
        thirdGrade.setStudent(student);
        thirdGrade.setGradeType(GradeType.EXAM);
        thirdGrade.setSubject(subjectEntity);
        thirdGrade.setTeacher(teacher);
        getGradeRepository().save(thirdGrade);
    }
}
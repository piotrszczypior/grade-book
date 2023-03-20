package com.capgemini.gradebook.average;

import com.capgemini.gradebook.AbstractRepositorySupport;
import com.capgemini.gradebook.domain.AverageEto;
import com.capgemini.gradebook.persistence.entity.*;
import com.capgemini.gradebook.persistence.entity.data.SubjectType;
import com.capgemini.gradebook.service.impl.AverageServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.inject.Inject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@SpringBootTest
class AverageServiceTest extends AbstractRepositorySupport {

    @Inject
    private AverageServiceImpl testedClass;

    @AfterEach
    @BeforeEach
    @Override
    public void cleanDb() {
        super.cleanDb();
    }

    @Test
    @DisplayName("Test calculating grades' average")
    void calculateAverage() {
        //given
        ClassYearEntity classYearEntity = getClassYearEntity();
        StudentEntity student = getStudent(classYearEntity);
        TeacherEntity teacher = getTeacher();
        SubjectEntity subjectEntity = getSubjectEntity(classYearEntity, teacher);

        Double expectedAverage = createTestData(student, teacher, subjectEntity);

        //when
        List<AverageEto> averageList = this.testedClass.calculateAveragePerSubject(student.getId());
        AverageEto result = averageList.stream().findFirst().get();

        //then
        assertNotNull(result);
        assertThat(result.getGradeAverage()).isEqualTo(expectedAverage);
        assertThat(result.getSubjectName()).isEqualTo(subjectEntity.getName());

    }


    private Double createTestData(StudentEntity savedStudent, TeacherEntity teacher, SubjectEntity subjectEntity) {
        int weights = 0;
        double sumOfWeight = 0;

        List<GradeEntity> grades = createGrades(savedStudent, subjectEntity, teacher);

        for (var grade : grades) {
            sumOfWeight += grade.getValue().doubleValue() * grade.getGradeWeight().doubleValue();
        }

        for (var grade : grades) {
            weights += grade.getGradeWeight().doubleValue();
        }

        return new BigDecimal(sumOfWeight / weights).setScale(3, RoundingMode.HALF_UP).doubleValue();
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

    private List<GradeEntity> createGrades(StudentEntity savedStudent, SubjectEntity subjectEntity, TeacherEntity teacher) {
        GradeEntity grade1 = new GradeEntity();

        grade1.setGradeAssigmentDate(LocalDate.parse("2022-03-23"));
        grade1.setGradeWeight(new BigDecimal(5));
        grade1.setValue(4);
        grade1.setStudent(savedStudent);
        grade1.setSubject(subjectEntity);
        grade1.setTeacher(teacher);
        getGradeRepository().save(grade1);

        GradeEntity grade2 = new GradeEntity();

        grade2.setGradeAssigmentDate(LocalDate.parse("2022-03-24"));
        grade2.setGradeWeight(new BigDecimal(2));
        grade2.setValue(3);
        grade2.setStudent(savedStudent);
        grade2.setSubject(subjectEntity);
        grade2.setTeacher(teacher);
        getGradeRepository().save(grade2);

        return List.of(grade1, grade2);
    }

    private TeacherEntity getTeacher() {
        TeacherEntity teacher = new TeacherEntity();
        teacher.setFirstName("Elizabeth");
        teacher.setLastName("Smith");
        teacher = getTeacherRepository().save(teacher);
        return teacher;
    }
}
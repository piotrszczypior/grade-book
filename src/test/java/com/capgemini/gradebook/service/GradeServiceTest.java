package com.capgemini.gradebook.service;

import com.capgemini.gradebook.AbstractRepositorySupport;
import com.capgemini.gradebook.domain.GradeEto;
import com.capgemini.gradebook.persistence.entity.*;
import com.capgemini.gradebook.persistence.entity.data.GradeType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
class GradeServiceTest extends AbstractRepositorySupport {
    @Inject
    private GradeService testedClass;

    @AfterEach
    @Override
    public void cleanDb() {
        super.cleanDb();
    }

    @Test
    void findAllGrades_ShouldNotBeEmpty() {
        //given
        createTestData();

        //when
        List<GradeEto> result = this.testedClass.findAllGrades();

        //then
        Assertions.assertThat(result).isNotEmpty();
    }

    @Test
    void findGradeById_ShouldNotBeNull() {
        //given
        GradeEntity grade = createTestData();

        //when
        GradeEto result = this.testedClass.findGradeById(grade.getId());

        //then
        Assertions.assertThat(result).isNotNull();

    }

    @Test
    void save_ShouldCreateEntityInDB() {
        //given
        GradeEto gradeToSave = createSaveTestData();

        //when
        this.testedClass.save(gradeToSave);
        List<GradeEntity> result = getGradeRepository().findAll();

        //then
        Assertions.assertThat(result).isNotEmpty();
    }

    @Test
    void delete_ShouldDeleteEntityFromDB() {
        //given
        GradeEntity gradeToDelete = createTestData();

        //when
        this.testedClass.delete(gradeToDelete.getId());
        List<GradeEntity> result = getGradeRepository().findAll();

        //then
        Assertions.assertThat(result).isEmpty();
    }

    private GradeEntity createTestData() {
        TeacherEntity teacher = new TeacherEntity();
        getTeacherRepository().save(teacher);

        StudentEntity student = new StudentEntity();
        getStudentRepository().save(student);

        SubjectEntity subject = new SubjectEntity();
        getSubjectRepository().save(subject);

        GradeEntity grade = new GradeEntity();
        grade.setSubject(subject);
        grade.setTeacher(teacher);
        grade.setStudent(student);
        getGradeRepository().save(grade);

        return grade;
    }

    private GradeEto createSaveTestData() {
        ClassYearEntity classYear = new ClassYearEntity();
        classYear = getClassYearRepository().save(classYear);

        TeacherEntity teacher = new TeacherEntity();
        teacher = getTeacherRepository().save(teacher);

        StudentEntity student = new StudentEntity();
        student.setClassYear(classYear);
        student = getStudentRepository().save(student);

        SubjectEntity subject = new SubjectEntity();
        subject.setClassYear(classYear);
        subject.setTeacher(teacher);
        subject = getSubjectRepository().save(subject);

        GradeEto grade = new GradeEto();
        grade.setSubjectId(subject.getId());
        grade.setTeacherId(teacher.getId());
        grade.setStudentId(student.getId());

        grade.setGradeType(GradeType.TEST);
        grade.setValue(1);
        grade.setGradeWeight(new BigDecimal(3));
        grade.setComment("Excellent work!");

        return grade;
    }
}
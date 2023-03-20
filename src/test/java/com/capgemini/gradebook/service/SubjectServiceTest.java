package com.capgemini.gradebook.service;

import com.capgemini.gradebook.AbstractRepositorySupport;
import com.capgemini.gradebook.domain.SubjectEto;
import com.capgemini.gradebook.persistence.entity.ClassYearEntity;
import com.capgemini.gradebook.persistence.entity.SubjectEntity;
import com.capgemini.gradebook.persistence.entity.data.SubjectType;
import com.capgemini.gradebook.persistence.entity.TeacherEntity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SubjectServiceTest extends AbstractRepositorySupport {

    @Inject
    private SubjectService testedClass;

    @AfterEach
    @Override
    public void cleanDb() {
        super.cleanDb();
    }

    @Test
    public void findAllSubjects_ShouldNotBeEmpty() {
        //given
        saveTestData();

        //when
        List<SubjectEto> result = this.testedClass.findAllSubjects();

        //Then
       assertThat(result).isNotEmpty();
    }

    @Test
    public void findSubjectById_ShouldNotBeEmpty() {
        //given
        SubjectEntity savedData = saveTestData();

        //when
        SubjectEto result = this.testedClass.findSubjectById(savedData.getId());

        //Then
        assertThat(result).isNotNull();
    }

    @Test
    public void findSubjectByType_ShouldNotBeNull() {
        //given
        SubjectEntity savedData = saveTestData();

        //when
        List<SubjectEto> result = this.testedClass.findSubjectByType(savedData.getSubjectType());

        //then
        assertThat(result).isNotEmpty();
    }

    @Test
    public void save_ShouldNotReturnNull() {
        //given
        SubjectEntity subject = saveTestData();
        SubjectEto newSubject = new SubjectEto();
        newSubject.setClassYearId(subject.getClassYear().getId());
        newSubject.setTeacherId(subject.getTeacher().getId());

        //when
        SubjectEto result = this.testedClass.save(newSubject);

        //then
        assertThat(result).isNotNull();
    }

    @Test
    public void delete_ShouldDeleteObject() {
        //given
        SubjectEntity subjectToDelete = saveTestData();

        //when
        this.testedClass.delete(subjectToDelete.getId());
        List<SubjectEntity> subjects = getSubjectRepository().findAll();

        //then
        assertThat(subjects).isEmpty();
    }


    private SubjectEntity saveTestData() {

        TeacherEntity teacherEntity = new TeacherEntity();
        getTeacherRepository().save(teacherEntity);

        ClassYearEntity classYear = new ClassYearEntity();
        getClassYearRepository().save(classYear);

        SubjectEntity subjectEntity = new SubjectEntity();
        subjectEntity.setSubjectType(SubjectType.PE);
        subjectEntity.setName("Biology_7C");
        subjectEntity.setClassYear(classYear);
        subjectEntity.setTeacher(teacherEntity);

        getSubjectRepository().save(subjectEntity);

        return subjectEntity;
    }
}
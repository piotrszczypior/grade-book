package com.capgemini.gradebook.features;

import com.capgemini.gradebook.AbstractRepositorySupport;
import com.capgemini.gradebook.domain.SubjectEto;
import com.capgemini.gradebook.persistence.entity.ClassYearEntity;
import com.capgemini.gradebook.persistence.entity.data.SubjectType;
import com.capgemini.gradebook.persistence.entity.TeacherEntity;
import com.capgemini.gradebook.service.impl.SubjectServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SubjectServiceImplTest extends AbstractRepositorySupport {
    @Inject
    private SubjectServiceImpl testedClass;

    @Test
    void save_shouldCreateNameFieldInSubjectEntity() {
        //given
        SubjectEto subjectToSave = createTestData();
        String subjectName = subjectToSave.getSubjectType() + "_3a";

        //when
        SubjectEto result = this.testedClass.save(subjectToSave);

        //then
        assertNotNull(result.getSubjectName());
        assertEquals(result.getSubjectName(), subjectName);
    }

    private SubjectEto createTestData() {
        TeacherEntity teacher = new TeacherEntity();
        teacher = getTeacherRepository().save(teacher);

        ClassYearEntity classYear = new ClassYearEntity();
        classYear.setClassLevel(3);
        classYear.setClassName("a");
        classYear.setClassYear("2019");
        classYear = getClassYearRepository().save(classYear);

        SubjectEto subjectEto = new SubjectEto();
        subjectEto.setClassYearId(classYear.getId());
        subjectEto.setTeacherId(teacher.getId());

        subjectEto.setSubjectType(SubjectType.PE);

        return subjectEto;
    }
}
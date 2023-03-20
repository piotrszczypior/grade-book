package com.capgemini.gradebook.service;

import com.capgemini.gradebook.domain.SubjectEto;
import com.capgemini.gradebook.persistence.entity.data.SubjectType;

import java.util.List;

public interface SubjectService {

    List<SubjectEto> findAllSubjects();

    List<SubjectEto> findSubjectByType(final SubjectType subjectType);

    SubjectEto findSubjectById(Long id);

    SubjectEto save(SubjectEto subject);

    void delete(Long id);
}

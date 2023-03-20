package com.capgemini.gradebook.service;

import com.capgemini.gradebook.domain.GradeEto;

import java.util.List;

public interface GradeService {

    List<GradeEto> findAllGrades();

    GradeEto findGradeById(Long id);

    GradeEto save(GradeEto grade);

    void delete(Long id);
}

package com.capgemini.gradebook.service;

import com.capgemini.gradebook.domain.GradeEto;
import com.capgemini.gradebook.domain.GradeParamsEto;

import java.util.List;

public interface QueryGradeService {

    List<GradeEto> findGradesByParams(GradeParamsEto gradeParams);
}

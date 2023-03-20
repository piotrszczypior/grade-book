package com.capgemini.gradebook.persistence.repo.custom;

import com.capgemini.gradebook.domain.GradeParamsEto;
import com.capgemini.gradebook.persistence.entity.GradeEntity;

import java.util.List;

public interface CustomGradeRepository {
    List<GradeEntity> findGamesByParams(GradeParamsEto gradeParams);
}

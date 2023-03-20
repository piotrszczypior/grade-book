package com.capgemini.gradebook.service.impl;

import com.capgemini.gradebook.domain.GradeEto;
import com.capgemini.gradebook.domain.GradeParamsEto;
import com.capgemini.gradebook.domain.mapper.support.AbstractMapperSupport;
import com.capgemini.gradebook.persistence.repo.GradeRepository;
import com.capgemini.gradebook.service.QueryGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class QueryGradeServiceImpl extends AbstractMapperSupport implements QueryGradeService {

    private final GradeRepository gradeRepository;

    @Autowired
    public QueryGradeServiceImpl(final GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    @Override
    public List<GradeEto> findGradesByParams(GradeParamsEto gradeParams) {
        return getGradeMapper().mapToETOList(gradeRepository.findGamesByParams(gradeParams));
    }
}

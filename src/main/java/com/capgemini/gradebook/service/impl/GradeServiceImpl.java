package com.capgemini.gradebook.service.impl;

import com.capgemini.gradebook.domain.GradeEto;
import com.capgemini.gradebook.domain.mapper.support.AbstractMapperSupport;
import com.capgemini.gradebook.exception.NoArgumentException;
import com.capgemini.gradebook.exception.NotFoundException;
import com.capgemini.gradebook.persistence.entity.GradeEntity;
import com.capgemini.gradebook.persistence.repo.GradeRepository;
import com.capgemini.gradebook.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GradeServiceImpl extends AbstractMapperSupport implements GradeService {

    private final GradeRepository gradeRepository;

    @Autowired
    public GradeServiceImpl(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    @Override
    public List<GradeEto> findAllGrades() {
        return getGradeMapper().mapToETOList(this.gradeRepository.findAll());
    }

    @Override
    public GradeEto findGradeById(Long id) {

        Optional<GradeEntity> optionalGradeEntity = this.gradeRepository.findById(id);
        if (optionalGradeEntity.isEmpty()) {
            throw new NotFoundException("No grade found with id = " + id);
        }

        return getGradeMapper().map(optionalGradeEntity.get(), GradeEto.class);
    }

    @Override
    public GradeEto save(GradeEto grade) {

        if (grade.getGradeWeight() == null) {
            grade.setGradeWeight(new BigDecimal(1));
        }

        if ((grade.getValue() == 1 || grade.getValue() == 6) & grade.getComment() == null) {
            throw new NoArgumentException("Comment is required when grade is 1 or 6");
        }

        if (grade.getGradeType() == null) {
            throw new NoArgumentException("Grade should have type!");
        }

        GradeEntity entityToSave = getGradeMapper().map(grade, GradeEntity.class);

        return getGradeMapper().map(this.gradeRepository.save(entityToSave), GradeEto.class);
//        return getGradeMapper().map(this.gradeRepository.save(entityToSave), grade.getClass());
    }


    @Override
    public void delete(Long id) {
        Optional<GradeEntity> gradeOptional = this.gradeRepository.findById(id);

        if (gradeOptional.isEmpty()) {
            throw new NotFoundException("No subject with given id = " + id + " has been found.");
        }

        this.gradeRepository.deleteById(id);
    }
}

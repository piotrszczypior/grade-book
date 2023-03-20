package com.capgemini.gradebook.service.impl;

import com.capgemini.gradebook.domain.ClassYearEto;
import com.capgemini.gradebook.domain.mapper.support.AbstractMapperSupport;
import com.capgemini.gradebook.exception.NotFoundException;
import com.capgemini.gradebook.persistence.entity.ClassYearEntity;
import com.capgemini.gradebook.persistence.repo.ClassYearRepository;
import com.capgemini.gradebook.service.ClassYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClassYearServiceImpl extends AbstractMapperSupport implements ClassYearService {

    private final ClassYearRepository classYearRepository;

    @Autowired
    public ClassYearServiceImpl(ClassYearRepository classYearRepo) {
        this.classYearRepository = classYearRepo;
    }

    @Override
    public List<ClassYearEto> findAllClasses() {
        return getClassYearMapper().mapToETOList(this.classYearRepository.findAll());
    }


    @Override
    public List<ClassYearEto> findClassYearByClassLevel(Integer classLevel) {
        List<ClassYearEntity> classesByClassYear = this.classYearRepository.findAll()
                .stream().filter(classYearEntity -> classLevel.equals(classYearEntity.getClassLevel()))
                .collect(Collectors.toList());

        return getClassYearMapper().mapToETOList(classesByClassYear);
    }

    @Override
    public ClassYearEto findClassYearById(Long id) {
        Optional<ClassYearEntity> optionalClassYearEntity = this.classYearRepository.findById(id);

        if (optionalClassYearEntity.isEmpty()) {
            throw new NotFoundException("No classYear found with id = " + id);
        }

        return getClassYearMapper().mapToETO(optionalClassYearEntity.get());
    }

    @Override
    public ClassYearEto save(ClassYearEto classYear) {
        ClassYearEntity subjectEntity = getClassYearMapper().mapToEntity(classYear);

        var classYearEn = this.classYearRepository.save(subjectEntity);
        return getClassYearMapper().mapToETO(classYearEn);
    }

    @Override
    public void delete(Long id) {
        Optional<ClassYearEntity> classYearOptional = this.classYearRepository.findById(id);

        if (classYearOptional.isEmpty()) {
            throw new NotFoundException("No subject with given id = " + id + " has been found.");
        }

        ClassYearEntity classYear = classYearOptional.get();
        classYear.getStudents().forEach(student -> student.setClassYear(null));
        classYear.getSubjects().forEach(subject -> subject.setClassYear(null));

        this.classYearRepository.delete(classYearOptional.get());
    }
}

package com.capgemini.gradebook.service.impl;

import com.capgemini.gradebook.domain.SubjectEto;
import com.capgemini.gradebook.domain.mapper.support.AbstractMapperSupport;
import com.capgemini.gradebook.exception.NotFoundException;
import com.capgemini.gradebook.persistence.entity.SubjectEntity;
import com.capgemini.gradebook.persistence.entity.data.SubjectType;
import com.capgemini.gradebook.persistence.repo.SubjectRepository;
import com.capgemini.gradebook.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class SubjectServiceImpl extends AbstractMapperSupport implements SubjectService {

    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectServiceImpl(final SubjectRepository subjectRepo) {
        this.subjectRepository = subjectRepo;
    }

    @Override
    public List<SubjectEto> findAllSubjects() {
        return getSubjectMapper().mapToETOList(this.subjectRepository.findAll());
    }

    @Override
    public List<SubjectEto> findSubjectByType(SubjectType subjectType) {
        List<SubjectEntity> entitiesWithGivenSubjectType = this.subjectRepository.findAll()
                .stream().filter(subject -> subject.getSubjectType().equals(subjectType))
                .collect(Collectors.toList());


        return getSubjectMapper().mapToETOList(entitiesWithGivenSubjectType);
    }

    @Override
    public SubjectEto findSubjectById(Long id) {
        Optional<SubjectEntity> subjectOptional = this.subjectRepository.findById(id);

        if (subjectOptional.isEmpty()) {
            throw new NotFoundException("No subject with given id = " + id + " has been found.");
        }

        return getSubjectMapper().mapToETO(subjectOptional.get());
    }

    @Override
    public SubjectEto save(SubjectEto subject) {

        SubjectEntity subjectEntity = getSubjectMapper().mapToEntity(subject);

        String name = subjectEntity.getSubjectType() +
                "_" + subjectEntity.getClassYear().getClassLevel() +
                subjectEntity.getClassYear().getClassName();

        if (!Objects.equals(subjectEntity.getName(), name)) {
            subjectEntity.setName(name);
        }

        return getSubjectMapper().mapToETO(this.subjectRepository.save(subjectEntity));
    }

    @Override
    public void delete(Long id) {
        Optional<SubjectEntity> subjectOptional = this.subjectRepository.findById(id);

        if (subjectOptional.isEmpty()) {
            throw new NotFoundException("No subject with given id = " + id + " has been found.");
        }

        SubjectEntity subjectEntity = subjectOptional.get();
        subjectEntity.getGrades().forEach(grade -> grade.setSubject(null));

        subjectEntity.getTeacher().getSubjects().remove(subjectEntity);
        subjectEntity.getClassYear().getSubjects().remove(subjectEntity);

        this.subjectRepository.deleteById(id);
    }
}

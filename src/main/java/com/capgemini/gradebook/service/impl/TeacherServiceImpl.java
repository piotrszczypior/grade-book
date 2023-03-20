package com.capgemini.gradebook.service.impl;

import com.capgemini.gradebook.domain.TeacherEto;
import com.capgemini.gradebook.domain.mapper.support.AbstractMapperSupport;
import com.capgemini.gradebook.exception.NotFoundException;
import com.capgemini.gradebook.persistence.entity.TeacherEntity;
import com.capgemini.gradebook.persistence.repo.TeacherRepository;
import com.capgemini.gradebook.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TeacherServiceImpl extends AbstractMapperSupport implements TeacherService {

    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherServiceImpl(final TeacherRepository teacherRepository) {

        this.teacherRepository = teacherRepository;
    }

    @Override
    public List<TeacherEto> findAllTeachers() {

        return getTeacherMapper().mapToETOList(this.teacherRepository.findAll());
    }

    @Override
    public List<TeacherEto> findTeacherByLastName(final String lastname) {

        final List<TeacherEntity> teachers = this.teacherRepository.findTeacherByLastName(lastname);
        return teachers.stream().map(getTeacherMapper()::mapToETO).collect(Collectors.toList());
    }

    @Override
    public TeacherEto findTeacherById(Long id) {

        final Optional<TeacherEntity> result = this.teacherRepository.findById(id);
        return result.map(getTeacherMapper()::mapToETO)
                .orElseThrow(() -> new NotFoundException("Teacher not found exception"));
    }

    @Override
    public TeacherEto save(TeacherEto newTeacher) {

        TeacherEntity teacherEntity = getTeacherMapper().mapToEntity(newTeacher);

        teacherEntity = this.teacherRepository.save(teacherEntity);
        return getTeacherMapper().mapToETO(teacherEntity);
    }

    @Override
    public void delete(Long id) {

        Optional<TeacherEntity> teacherOptional = this.teacherRepository.findById(id);

        if (teacherOptional.isEmpty()) {
            throw new NotFoundException("No teacher with given id = " + id + " has been found.");
        }

        TeacherEntity teacher = teacherOptional.get();
        teacher.getSubjects().forEach(subject -> subject.setTeacher(null));
//        teacher.getSubjectEntityList().clear();

        teacher.getGrades().forEach(grade -> grade.setTeacher(null));

        this.teacherRepository.deleteById(id);
    }
}

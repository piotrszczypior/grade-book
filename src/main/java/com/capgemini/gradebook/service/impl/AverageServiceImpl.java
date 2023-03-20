package com.capgemini.gradebook.service.impl;

import com.capgemini.gradebook.domain.AverageEto;
import com.capgemini.gradebook.domain.parser.AverageParser;
import com.capgemini.gradebook.persistence.entity.GradeEntity;
import com.capgemini.gradebook.persistence.entity.StudentEntity;
import com.capgemini.gradebook.persistence.entity.SubjectEntity;
import com.capgemini.gradebook.persistence.repo.StudentRepository;
import com.capgemini.gradebook.service.AverageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AverageServiceImpl implements AverageService {
    private final StudentRepository studentRepository;


    @Autowired
    public AverageServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<AverageEto> calculateAveragePerSubject(Long studentId) {
        if (studentId == null) {
           return null;
        }

        Optional<StudentEntity> optionalStudentEntity = this.studentRepository.findById(studentId);

        if (optionalStudentEntity.isEmpty()) {
            return Collections.emptyList();
        }

        StudentEntity student = optionalStudentEntity.get();

        Map<SubjectEntity, List<GradeEntity>> gradesPerSubject = student.getGrades()
                .stream()
                .collect(Collectors.groupingBy(GradeEntity::getSubject));

        Map<SubjectEntity, Double> averagePerSubject = gradesPerSubject.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, listEntry -> getAverage(listEntry.getValue())));

        List<AverageEto> averages = averagePerSubject.entrySet()
                .stream()
                .map(entry -> AverageParser.mapToAverageEto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        return averages;
    }

    private Double getAverage(List<GradeEntity> grades) {

        Double weight = grades.stream()
                .map(GradeEntity::getGradeWeight)
                .mapToDouble(Number::doubleValue)
                .sum();

        Double sum = grades.stream()
                .mapToDouble(gradeEntity -> gradeEntity.getGradeWeight().doubleValue()
                        * gradeEntity.getValue().doubleValue())
                .sum();

        return sum / weight;
    }

}
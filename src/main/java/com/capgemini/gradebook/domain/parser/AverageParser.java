package com.capgemini.gradebook.domain.parser;

import com.capgemini.gradebook.domain.AverageEto;
import com.capgemini.gradebook.persistence.entity.SubjectEntity;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AverageParser {

    public static AverageEto mapToAverageEto(SubjectEntity subject, Double average) {
        AverageEto averageEto = new AverageEto();

        averageEto.setGradeAverage(new BigDecimal(average).setScale(3, RoundingMode.HALF_UP).doubleValue());
        averageEto.setSubjectName(subject.getName());

        return averageEto;
    }
}

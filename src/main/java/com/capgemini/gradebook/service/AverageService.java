package com.capgemini.gradebook.service;

import com.capgemini.gradebook.domain.AverageEto;

import java.util.List;

public interface AverageService {

    List<AverageEto> calculateAveragePerSubject(Long studentId);
}

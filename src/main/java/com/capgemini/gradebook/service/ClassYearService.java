package com.capgemini.gradebook.service;

import com.capgemini.gradebook.domain.ClassYearEto;

import java.util.List;

public interface ClassYearService {
    List<ClassYearEto> findAllClasses();

    List<ClassYearEto> findClassYearByClassLevel(final Integer classLevel);

    ClassYearEto findClassYearById(Long id);

    ClassYearEto save(ClassYearEto classYear);

    void delete(Long id);
}

package com.capgemini.gradebook.persistence.repo;

import com.capgemini.gradebook.persistence.entity.ClassYearEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.inject.Named;

public interface ClassYearRepository extends JpaRepository<ClassYearEntity, Long> {
}

package com.capgemini.gradebook.persistence.repo;

import com.capgemini.gradebook.persistence.entity.GradeEntity;
import com.capgemini.gradebook.persistence.repo.custom.CustomGradeRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<GradeEntity, Long>, CustomGradeRepository {
}

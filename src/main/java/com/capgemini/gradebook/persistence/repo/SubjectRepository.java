package com.capgemini.gradebook.persistence.repo;

import com.capgemini.gradebook.persistence.entity.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<SubjectEntity, Long> {
}

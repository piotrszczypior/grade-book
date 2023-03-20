package com.capgemini.gradebook.persistence.repo;

import com.capgemini.gradebook.persistence.entity.StudentEntity;
import com.capgemini.gradebook.persistence.repo.custom.CustomStudentRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<StudentEntity, Long>, CustomStudentRepository {
}

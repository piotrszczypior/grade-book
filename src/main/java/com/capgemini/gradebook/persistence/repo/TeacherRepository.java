package com.capgemini.gradebook.persistence.repo;

import com.capgemini.gradebook.persistence.entity.TeacherEntity;
import com.capgemini.gradebook.persistence.repo.custom.CustomTeacherRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<TeacherEntity, Long>, CustomTeacherRepository {

}

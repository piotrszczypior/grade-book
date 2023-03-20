package com.capgemini.gradebook.controller;

import com.capgemini.gradebook.domain.StudentEto;
import com.capgemini.gradebook.domain.StudentQueryEto;
import com.capgemini.gradebook.service.StudentQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@Valid
@RequestMapping("/rest")
public class StudentQueryController {
    private final StudentQueryService studentQueryService;

    @Autowired
    public StudentQueryController(final StudentQueryService studentQueryService) {

        this.studentQueryService = studentQueryService;
    }

    @GetMapping("students-failed/{date}")
    public ResponseEntity<List<StudentEto>> getStudents(@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return ResponseEntity.ok(studentQueryService.findStudentByDateWhoWereGivenGradeEqualToOne(date));
    }

    @GetMapping("students-query")
    public ResponseEntity<Long> getStudents(StudentQueryEto studentQueryEto) {
        return ResponseEntity.ok(studentQueryService.findNumberOfStudentsByValueAndDate(studentQueryEto));
    }

}

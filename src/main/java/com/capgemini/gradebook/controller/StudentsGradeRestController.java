package com.capgemini.gradebook.controller;

import com.capgemini.gradebook.domain.AverageEto;
import com.capgemini.gradebook.service.AverageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/rest")
@RestController
public class StudentsGradeRestController {

    private final AverageService averageService;

    @Autowired
    public StudentsGradeRestController(AverageService averageService) {
        this.averageService = averageService;
    }

    @GetMapping("/student-grades/{id}")
    public ResponseEntity<List<AverageEto>> getAverage(@PathVariable("id") final Long studentId) {
        List<AverageEto> result = this.averageService.calculateAveragePerSubject(studentId);

        if(result == null) {
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok().body(result);
    }
}

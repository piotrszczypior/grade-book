package com.capgemini.gradebook.controller;

import com.capgemini.gradebook.domain.GradeEto;
import com.capgemini.gradebook.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Valid
@RequestMapping("/rest")
public class GradeRestController {


    private final GradeService gradeService;

    @Autowired
    public GradeRestController(final GradeService gradeService) {

        this.gradeService = gradeService;
    }

    @GetMapping("/grades")
    public ResponseEntity<List<GradeEto>> findAllGrades() {

        final List<GradeEto> allGrades = this.gradeService.findAllGrades();
        return ResponseEntity.ok().body(allGrades);
    }

    @GetMapping("/grades/{id}")
    public ResponseEntity<GradeEto> findGradeById(@PathVariable("id") final Long id) {

        final GradeEto grade = this.gradeService.findGradeById(id);
        return ResponseEntity.ok().body(grade);
    }

    @PostMapping("/grades")
    public GradeEto addGrade(@RequestBody GradeEto newGrade) {
        return this.gradeService.save(newGrade);
    }

    @PutMapping("/grades/{id}")
    GradeEto updateGrades(@RequestBody GradeEto grade) {
        return this.gradeService.save(grade);
    }

    @DeleteMapping("/grades/{id}")
    void deleteGrade(@PathVariable Long id) {
        this.gradeService.delete(id);
    }
}

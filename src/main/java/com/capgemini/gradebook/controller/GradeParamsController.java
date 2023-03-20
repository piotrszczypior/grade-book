package com.capgemini.gradebook.controller;

import com.capgemini.gradebook.domain.GradeEto;
import com.capgemini.gradebook.domain.GradeParamsEto;
import com.capgemini.gradebook.service.QueryGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@Valid
@RequestMapping("/rest")
public class GradeParamsController {


    private final QueryGradeService queryGradeService;

    @Autowired
    public GradeParamsController(final QueryGradeService queryGradeService) {

        this.queryGradeService = queryGradeService;
    }

    @GetMapping("grade-params/")
    public ResponseEntity<List<GradeEto>> getGrades(GradeParamsEto gradeParamsEto) {
        List<GradeEto> foundGames = this.queryGradeService.findGradesByParams(gradeParamsEto);
        return ResponseEntity.ok().body(foundGames);
    }
}

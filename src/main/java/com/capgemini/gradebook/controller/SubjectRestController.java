package com.capgemini.gradebook.controller;

import com.capgemini.gradebook.domain.SubjectEto;
import com.capgemini.gradebook.persistence.entity.data.SubjectType;
import com.capgemini.gradebook.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/rest")
@Valid
public class SubjectRestController {

    private final SubjectService subjectService;

    @Autowired
    public SubjectRestController(final SubjectService subjectService) {

        this.subjectService = subjectService;
    }

    @GetMapping("/subjects")
    public ResponseEntity<List<SubjectEto>> findAllSubjects() {

        final List<SubjectEto> allSubjects = this.subjectService.findAllSubjects();
        return ResponseEntity.ok().body(allSubjects);
    }

    @GetMapping("/subjects/{id}")
    public ResponseEntity<SubjectEto> findSubjectById(@PathVariable("id") final Long id) {

        final SubjectEto subject = this.subjectService.findSubjectById(id);
        return ResponseEntity.ok().body(subject);
    }

    @GetMapping("/subjects/subjectType/{subjectType}")
    public ResponseEntity<List<SubjectEto>> findSubjectByName(@PathVariable("subjectType") final SubjectType subjectType) {

        final List<SubjectEto> subjects = this.subjectService.findSubjectByType(subjectType);
        return ResponseEntity.ok().body(subjects);
    }

    @PostMapping("/subjects")
    public SubjectEto addSubject(@RequestBody SubjectEto newSubject) {

        return this.subjectService.save(newSubject);
    }

    @PutMapping("/subjects/{id}")
    SubjectEto updateSubject(@RequestBody SubjectEto subject) {

        //dev note: you can ignore the lack of createdDate and updateDate on resulting object, it will exists in database
        //its just not "available" during the return by update; if you want, you can try to fix it
        return this.subjectService.save(subject);
    }

    @DeleteMapping("/subjects/{id}")
    void deleteSubject(@PathVariable Long id) {
        this.subjectService.delete(id);
    }

}

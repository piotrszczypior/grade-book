package com.capgemini.gradebook.controller;

import com.capgemini.gradebook.domain.ClassYearEto;
import com.capgemini.gradebook.service.ClassYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * @name ClassYearRestController: RestController
 *
 * @methods
 * GET  findAllClasses /rest/classes
 * GET findClassById /rest/classes/{id}
 * GET findClassByLevel /rest/classes/class-level/{level}
 * POST addClass /rest/classes - body ClassYearEto
 * PUT updateClassYear /rest/classes/
 * DELETE deleteClassYear /rest/classes/{id}
 *
 * @throws [Exception type and description if any, otherwise leave this line out]
 *
 *
 */
@RestController
@Valid
@RequestMapping("/rest")
public class ClassYearRestController {

    private final ClassYearService classYearService;

    @Autowired
    public ClassYearRestController(final ClassYearService classYearService) {

        this.classYearService = classYearService;
    }

    @GetMapping("/classes")
    public ResponseEntity<List<ClassYearEto>> findAllClasses() {

        final List<ClassYearEto> allClasses = this.classYearService.findAllClasses();
        return ResponseEntity.ok().body(allClasses);
    }

    @GetMapping("/classes/{id}")
    public ResponseEntity<ClassYearEto> findClassById(@PathVariable("id") final Long id) {

        final ClassYearEto subject = this.classYearService.findClassYearById(id);
        return ResponseEntity.ok().body(subject);
    }

    @GetMapping("/classes/class-level/{level}")
    public ResponseEntity<List<ClassYearEto>> findClassByLevel(@PathVariable("level") final Integer classLevel) {
        if (classLevel == null) {
            return ResponseEntity.badRequest().build();
        }

        final List<ClassYearEto> classYearByName = this.classYearService.findClassYearByClassLevel(classLevel);

        if (classYearByName == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok().body(classYearByName);
    }

    @PostMapping("/classes")
    public ClassYearEto addClass(@RequestBody ClassYearEto newClass) {
        return this.classYearService.save(newClass);
    }

    @PutMapping("/classes/{id}")
    ClassYearEto updateClassYear(@RequestBody ClassYearEto classYearEto) {

        return this.classYearService.save(classYearEto);
    }

    @DeleteMapping("/classes/{id}")
    void deleteClassYear(@PathVariable Long id) {
        this.classYearService.delete(id);
    }
}

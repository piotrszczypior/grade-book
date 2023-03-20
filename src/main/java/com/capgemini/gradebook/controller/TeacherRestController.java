package com.capgemini.gradebook.controller;

import com.capgemini.gradebook.domain.TeacherEto;
import com.capgemini.gradebook.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * This is an example how to write some REST endpoints
 */
@RestController
@RequestMapping("/rest")
@Valid
public class TeacherRestController {

    private final TeacherService teacherService;

    @Autowired
    public TeacherRestController(final TeacherService teacherService) {

        this.teacherService = teacherService;
    }

    @GetMapping("/teachers")
    public ResponseEntity<List<TeacherEto>> findAllTeachers() {

        final List<TeacherEto> allTeachers = this.teacherService.findAllTeachers();
        return ResponseEntity.ok().body(allTeachers);
    }

    @GetMapping("/teachers/{id}")
    public ResponseEntity<TeacherEto> findTeacherById(@PathVariable("id") final Long id) {

        final TeacherEto teacher = this.teacherService.findTeacherById(id);
        return ResponseEntity.ok().body(teacher);
    }

    @GetMapping("/teachers/name/{name}")
    public ResponseEntity<List<TeacherEto>> findTeacherByLastname(@PathVariable("name") final String name) {

        final List<TeacherEto> teachers = this.teacherService.findTeacherByLastName(name);
        return ResponseEntity.ok().body(teachers);
    }

    @PostMapping("/teachers")
    public TeacherEto addTeacher(@RequestBody TeacherEto newTeacher) {

        return teacherService.save(newTeacher);
    }

    @PutMapping("/teachers/{id}")
    TeacherEto upsertEmployee(@RequestBody TeacherEto teacher) {

        //dev note: you can ignore the lack of createdDate and updateDate on resulting object, it will exists in database
        //its just not "available" during the return by update; if you want, you can try to fix it
        return this.teacherService.save(teacher);
    }

    @DeleteMapping("/teachers/{id}")
    void deleteEmployee(@PathVariable Long id) {
        this.teacherService.delete(id);
    }

}

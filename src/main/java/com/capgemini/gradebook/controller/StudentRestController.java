package com.capgemini.gradebook.controller;

import com.capgemini.gradebook.domain.StudentEto;
import com.capgemini.gradebook.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/rest/students")
@Valid
public class StudentRestController {

    private final StudentService studentService;

    @Autowired
    public StudentRestController(final StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping()
    public ResponseEntity<List<StudentEto>> findAllStudents() {

        final List<StudentEto> allStudents = this.studentService.findAllStudents();
        return ResponseEntity.ok().body(allStudents);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentEto> findStudentById(@PathVariable("id") final Long id) {

        final StudentEto subject = this.studentService.findStudentById(id);
        return ResponseEntity.ok().body(subject);
    }

    @GetMapping("/name/{lastname}")
    public ResponseEntity<List<StudentEto>> findSubjectByName(@PathVariable("lastname") final String lastname) {

        final List<StudentEto> students = this.studentService.findStudentByLastName(lastname);
        return ResponseEntity.ok().body(students);
    }

    @PostMapping()
    public StudentEto addStudent(@RequestBody StudentEto newStudent) {

        return this.studentService.save(newStudent);
    }

    @PutMapping("/{id}")
    public StudentEto updateStudent(@RequestBody StudentEto student) {

        //dev note: you can ignore the lack of createdDate and updateDate on resulting object, it will exists in database
        //its just not "available" during the return by update; if you want, you can try to fix it
        return this.studentService.save(student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        this.studentService.delete(id);
    }
}

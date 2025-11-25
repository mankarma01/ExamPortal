package com.logical.examportal.restcontroller;

import com.logical.examportal.entity.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/studentAPI")
public interface StudentController {
    @PostMapping("/create")
    ResponseEntity<?> create(@RequestBody Student student);

    @GetMapping("/get/{studentId}")
    ResponseEntity<?> getById(@PathVariable("studentId") Long studentId);

    @GetMapping("/getAll")
    ResponseEntity<?> getAll();

    @GetMapping("/getByCollegeId/{collegeId}")
    ResponseEntity<?> getByCollegeId(@PathVariable(name = "collegeId" , required = false) Long collegeId);

    @PutMapping("/update")
    ResponseEntity<?> update(@RequestBody Student student);

    @DeleteMapping("/delete/{studentId}")
    ResponseEntity<?> deleteById(@PathVariable("studentId") Long studentId);

    @PostMapping("/login")
    ResponseEntity<?> login(@RequestParam("username") String username, @RequestParam("password") String password);

    @PostMapping("/takeExam")
    ResponseEntity<?> takeExam(@RequestParam("email") String username);

    @GetMapping("/getByCityCollege/{cityId}/{collegeId}")
    ResponseEntity<?> getByCityCollege(@PathVariable("cityId") Long cityId, @PathVariable( name = "collegeId" , required = false) Long collegeId);

}

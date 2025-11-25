package com.logical.examportal.restcontroller;

import com.logical.examportal.entity.College;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/collegeAPI")
public interface CollegeController {

    @PostMapping("/create")
    ResponseEntity<?> create(@RequestBody College college);

    @PutMapping("/update")
    ResponseEntity<?> update(@RequestBody College college);

    @GetMapping("/getAll")
    ResponseEntity<?> getAll();

    @GetMapping("/get/{collegeId}")
    ResponseEntity<?> getById(@PathVariable("collegeId") Long collegeId);

    @DeleteMapping("/delete/{collegeId}")
    ResponseEntity<?> deleteById(@PathVariable("collegeId") Long collegeId);

    @GetMapping("/getActiveByCity/{cityId}")
    ResponseEntity<?> getActiveByCity(@PathVariable("cityId") Long cityId);
}

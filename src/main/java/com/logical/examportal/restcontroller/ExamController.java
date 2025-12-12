package com.logical.examportal.restcontroller;

import com.logical.examportal.entity.Exam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/examAPI")
public interface ExamController {

    @PostMapping("/create")
    ResponseEntity<?> create(@RequestBody Exam exam);

    @PutMapping("/update")
    ResponseEntity<?> update(@RequestBody Exam exam);

    @GetMapping("/getAll")
    ResponseEntity<?> getAll();

    @GetMapping("/get/{examId}")
    ResponseEntity<?> getById(@PathVariable("examId") Long examId);

    @DeleteMapping("/delete/{examId}")
    ResponseEntity<?> deleteById(@PathVariable("examId") Long examId);

    @GetMapping("/existsCode/{examId}/{examCode}")
    ResponseEntity<?> alreadyExistsCode(@PathVariable("examId") Long examId, @PathVariable("examCode") String examCode);

    @GetMapping("/getActive")
    ResponseEntity<?> getActiveList();
    
    @GetMapping("/getByCode/{examCode}")
    ResponseEntity<?> getExamByCode(@PathVariable("examCode") String examCode);


}

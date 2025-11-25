package com.logical.examportal.restcontroller;

import com.logical.examportal.entity.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/resultAPI")
public interface ResultController {

    @PostMapping("/create")
    ResponseEntity<?>create(@RequestBody Result result);

    @GetMapping("/get/{resultId}")
    ResponseEntity<?>getById(@PathVariable("resultId") Long resultId);

    @GetMapping("/getAll")
    ResponseEntity<?>getAll();

    @GetMapping("/getAllByExam/{examId}")
    ResponseEntity<?>getAllByExamId(@PathVariable("examId") Long examId,  @RequestParam(name = "rs", required = false, defaultValue = "") String resultStatus,
                                    @RequestParam(name = "mm", required = false, defaultValue = "0") int minimumMarks, @RequestParam(name = "ed", required = false, defaultValue = "") String examDate,
                                    @RequestParam(name = "cId", required = false, defaultValue = "0") Long collegeId);

    @DeleteMapping("/delete/{resultId}")
    ResponseEntity<?>deleteById(@PathVariable("resultId") Long resultId);

    @DeleteMapping("/status/{resultId}")
    ResponseEntity<?>changeStatusById(@PathVariable("resultId") Long resultId);

    @PostMapping("/createExam")
    ResponseEntity<?>createExam(@RequestParam("studentId") Long studentId, @RequestParam("examId") Long examId);

}

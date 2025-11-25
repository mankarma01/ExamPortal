package com.logical.examportal.restcontroller;

import com.logical.examportal.entity.Question;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/questionAPI")
public interface QuestionController {

    @PostMapping("/uploadCSV")
    ResponseEntity<?> uploadCSVQuestion(@RequestParam("file") MultipartFile file, @RequestParam("examId") Long examId, @RequestParam("examSet") Character examSet);

    @PostMapping("/reUploadCSV")
    ResponseEntity<?> reUploadCSVQuestion(@RequestParam("file") MultipartFile file, @RequestParam("examId") Long examId);

    @PostMapping("/create")
    ResponseEntity<?>create(@RequestBody Question question);

    @GetMapping("/get/{questionId}")
    ResponseEntity<?>getById(@PathVariable("questionId") Long questionId);

    @GetMapping("/getByExamId/{examId}")
    ResponseEntity<?>getByExamId(@PathVariable("examId") Long examId);

    @GetMapping("/getAll")
    ResponseEntity<?>getAll();

    @DeleteMapping("/delete/{questionId}")
    ResponseEntity<?>deleteById(@PathVariable("questionId") Long questionId);

    @DeleteMapping("/deleteAll/{examId}/{examSet}")
    ResponseEntity<?>deleteQuestionSet(@PathVariable("examId") Long examId, @PathVariable("examSet") Character examSet);

}

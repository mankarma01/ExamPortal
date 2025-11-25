package com.logical.examportal.restcontroller;

import com.logical.examportal.entity.ExamRules;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/examRulesAPI")
public interface ExamRulesController {

    @PostMapping("/create")
    ResponseEntity<?> create(@RequestBody ExamRules examRules);

    @PutMapping("/update")
    ResponseEntity<?> update(@RequestBody ExamRules examRules);

    @GetMapping("/getAll")
    ResponseEntity<?> getAll();

    @GetMapping("/get/{examRulesId}")
    ResponseEntity<?> getById(@PathVariable("examRulesId") Long examRulesId);

    @DeleteMapping("/delete/{examRulesId}")
    ResponseEntity<?> deleteById(@PathVariable("examRulesId") Long examRulesId);
}

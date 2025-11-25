package com.logical.examportal.restcontroller;

import com.logical.examportal.entity.TermsCondition;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/termsConditionAPI")
public interface TermsConditionController {

    @PostMapping("/create")
    ResponseEntity<?> create(@RequestBody TermsCondition termsCondition);

    @PutMapping("/update")
    ResponseEntity<?> update(@RequestBody TermsCondition termsCondition);

    @GetMapping("/getAll")
    ResponseEntity<?> getAll();

    @GetMapping("/get/{termsConditionId}")
    ResponseEntity<?> getById(@PathVariable("termsConditionId") Long examId);

    @DeleteMapping("/delete/{termsConditionId}")
    ResponseEntity<?> deleteById(@PathVariable("termsConditionId") Long examId);
}

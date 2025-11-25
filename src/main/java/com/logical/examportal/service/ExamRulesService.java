package com.logical.examportal.service;

import com.logical.examportal.entity.ExamRules;
import org.springframework.http.ResponseEntity;

public interface ExamRulesService {

    ResponseEntity<?> create(ExamRules examRules);
    ResponseEntity<?> getAll();
    ResponseEntity<?> getById(Long examRulesId);
    ResponseEntity<?> update(ExamRules examRules);
    ResponseEntity<?> deleteById(Long examRulesId);

}

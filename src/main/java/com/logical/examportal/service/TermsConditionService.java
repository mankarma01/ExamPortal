package com.logical.examportal.service;

import com.logical.examportal.entity.TermsCondition;
import org.springframework.http.ResponseEntity;

public interface TermsConditionService {

    ResponseEntity<?> create(TermsCondition termsCondition);
    ResponseEntity<?> getAll();
    ResponseEntity<?> getById(Long examId);
    ResponseEntity<?> update(TermsCondition termsCondition);
    ResponseEntity<?> deleteById(Long examId);

}

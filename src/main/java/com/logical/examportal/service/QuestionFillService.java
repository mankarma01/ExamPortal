package com.logical.examportal.service;


import com.logical.examportal.entity.QuestionFill;
import org.springframework.http.ResponseEntity;

public interface QuestionFillService {
    ResponseEntity<?> checkedQuestion(QuestionFill questionFill);
    ResponseEntity<?> getAllQuestionFill();
    ResponseEntity<?> getAllQuestionFillByResultId(Long resultId);
}

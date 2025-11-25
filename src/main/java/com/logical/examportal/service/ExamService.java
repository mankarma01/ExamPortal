package com.logical.examportal.service;

import com.logical.examportal.entity.Exam;
import org.springframework.http.ResponseEntity;

public interface ExamService {

    ResponseEntity<?> create(Exam exam);
    ResponseEntity<?> getAll();
    ResponseEntity<?> getById(Long examId);
    ResponseEntity<?> update(Exam exam);
    ResponseEntity<?> deleteById(Long examId);
    ResponseEntity<?> alreadyExistsCode(Long examId, String examCode);

}

package com.logical.examportal.service;

import com.logical.examportal.entity.Result;
import org.springframework.http.ResponseEntity;

public interface ResultService {
    ResponseEntity<?> create(Result result);
    ResponseEntity<?> update(Result result);
    ResponseEntity<?> getById(Long resultId);
    ResponseEntity<?> getAll();
    ResponseEntity<?> getAllByExamId(Long examId, String resultStatus, int minimumMarks, String examDate, Long collegeId);
    ResponseEntity<?> createExam(Long studentId, Long examId);
    ResponseEntity<?> deleteById(Long resultId);
    ResponseEntity<?> changeStatusById(Long resultId);
}

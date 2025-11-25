package com.logical.examportal.service;

import com.logical.examportal.entity.Question;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

public interface QuestionService {
   ResponseEntity<?> create(Question question);
   ResponseEntity<?> createAll(ArrayList<Question> questionList);
   ResponseEntity<?> saveCSVQuestionData(MultipartFile file, Long examId, Character examSet);
   ResponseEntity<?> reSaveCSVQuestionData(MultipartFile file, Long examId);
   ResponseEntity<?> getAll();
   ResponseEntity<?> getById(Long questionId);
   ResponseEntity<?> getByExamId(Long examId);
   ResponseEntity<?> update(Question question);
   ResponseEntity<?> deleteById(Long questionId);
   ResponseEntity<?> deleteQuestionSet(Long examId, Character examSet);
}

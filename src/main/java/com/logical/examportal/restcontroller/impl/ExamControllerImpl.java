package com.logical.examportal.restcontroller.impl;

import com.logical.examportal.entity.Exam;
import com.logical.examportal.model.response.MessageResponse;
import com.logical.examportal.restcontroller.ExamController;
import com.logical.examportal.service.ExamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExamControllerImpl implements ExamController {
    @Autowired
    ExamService examService;
    private final Logger logger = LoggerFactory.getLogger(ExamControllerImpl.class);

    @Override
    public ResponseEntity<?> getById(Long examId) {
        try {
            return examService.getById(examId);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(
                    new MessageResponse(false,
                            "Something went wrong...Don't worry we are figuring out what went wrong...!"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> deleteById(Long examId) {
        try {
            return examService.deleteById(examId);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(
                    new MessageResponse(false,
                            "Something went wrong...Don't worry we are figuring out what went wrong...!"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> create(Exam exam) {
        try {
            return examService.create(exam);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(
                    new MessageResponse(false,
                            "Something went wrong...Don't worry we are figuring out what went wrong...!"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> update(Exam exam) {
        try {
            return examService.update(exam);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(
                    new MessageResponse(false,
                            "Something went wrong...Don't worry we are figuring out what went wrong...!"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getAll() {
        try {
            return examService.getAll();
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(
                    new MessageResponse(false,
                            "Something went wrong...Don't worry we are figuring out what went wrong...!"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> alreadyExistsCode(Long examId, String examCode) {
        try {
            return examService.alreadyExistsCode(examId, examCode);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(
                    new MessageResponse(false,
                            "Something went wrong...Don't worry we are figuring out what went wrong...!"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

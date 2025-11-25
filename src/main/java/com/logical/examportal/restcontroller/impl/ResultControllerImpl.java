package com.logical.examportal.restcontroller.impl;

import com.logical.examportal.entity.Result;
import com.logical.examportal.model.response.MessageResponse;
import com.logical.examportal.repository.ExamRepository;
import com.logical.examportal.repository.StudentRepository;
import com.logical.examportal.restcontroller.ResultController;
import com.logical.examportal.service.ResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResultControllerImpl implements ResultController {

    @Autowired
    ResultService resultService;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    ExamRepository examRepository;

    private final Logger logger = LoggerFactory.getLogger(ResultControllerImpl.class);

    @Override
    public ResponseEntity<?> create(Result result) {

        try {
            return resultService.create(result);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(
                    new MessageResponse(false,
                            "Something went wrong...Don't worry we are figuring out what went wrong...!"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getById(Long resultId) {
        try {
            return resultService.getById(resultId);
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
            return resultService.getAll();
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(
                    new MessageResponse(false,
                            "Something went wrong...Don't worry we are figuring out what went wrong...!"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getAllByExamId(Long examId, String resultStatus, int minimumMarks, String examDate, Long collegeId) {
        try {
            return resultService.getAllByExamId(examId, resultStatus, minimumMarks, examDate, collegeId);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(
                    new MessageResponse(false,
                            "Something went wrong...Don't worry we are figuring out what went wrong...!"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> deleteById(Long resultId) {
        try {
            return resultService.deleteById(resultId);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(
                    new MessageResponse(false,
                            "Something went wrong...Don't worry we are figuring out what went wrong...!"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> changeStatusById(Long resultId) {
        try {
            return resultService.changeStatusById(resultId);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(
                    new MessageResponse(false,
                            "Something went wrong...Don't worry we are figuring out what went wrong...!"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> createExam(Long studentId, Long examId) {
        try {
            return resultService.createExam(studentId, examId);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(
                    new MessageResponse(false,
                            "Something went wrong...Don't worry we are figuring out what went wrong...!"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

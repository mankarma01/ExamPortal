package com.logical.examportal.restcontroller.impl;

import com.logical.examportal.entity.ExamRules;
import com.logical.examportal.model.response.MessageResponse;
import com.logical.examportal.restcontroller.ExamRulesController;
import com.logical.examportal.service.ExamRulesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExamRulesControllerImpl implements ExamRulesController {

    @Autowired
    ExamRulesService examRulesService;
    private final Logger logger = LoggerFactory.getLogger(ExamRulesControllerImpl.class);

    @Override
    public ResponseEntity<?> getById(Long examId) {
        try {
            return examRulesService.getById(examId);
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
            return examRulesService.deleteById(examId);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(
                    new MessageResponse(false,
                            "Something went wrong...Don't worry we are figuring out what went wrong...!"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> create(ExamRules exam) {
        try {
            return examRulesService.create(exam);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(
                    new MessageResponse(false,
                            "Something went wrong...Don't worry we are figuring out what went wrong...!"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> update(ExamRules exam) {
        try {
            return examRulesService.update(exam);
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
            return examRulesService.getAll();
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(
                    new MessageResponse(false,
                            "Something went wrong...Don't worry we are figuring out what went wrong...!"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

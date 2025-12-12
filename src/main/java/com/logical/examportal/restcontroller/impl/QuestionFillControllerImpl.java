package com.logical.examportal.restcontroller.impl;

import com.logical.examportal.entity.QuestionFill;
import com.logical.examportal.model.response.MessageResponse;
import com.logical.examportal.restcontroller.QuestionFillController;
import com.logical.examportal.service.QuestionFillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionFillControllerImpl implements QuestionFillController {

    @Autowired
    QuestionFillService questionFillService;

    private final Logger logger = LoggerFactory.getLogger(QuestionFillControllerImpl.class);

    @Override
    public ResponseEntity<?> checkedQuestion(QuestionFill questionFill) {
        try {
            return questionFillService.checkedQuestion(questionFill);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(
                    new MessageResponse(false,
                            "Something went wrong...Don't worry we are figuring out what went wrong...!"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //@Override
    public ResponseEntity<?> getAllQuestionFIll() {
        try {
            return questionFillService.getAllQuestionFill();
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(
                    new MessageResponse(false,
                            "Something went wrong...Don't worry we are figuring out what went wrong...!"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getAllQuestionFIllByResultId(Long resultId) {
        try {
            return questionFillService.getAllQuestionFillByResultId(resultId);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(
                    new MessageResponse(false,
                            "Something went wrong...Don't worry we are figuring out what went wrong...!"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

package com.logical.examportal.restcontroller.impl;

import com.logical.examportal.entity.College;
import com.logical.examportal.model.response.MessageResponse;
import com.logical.examportal.restcontroller.CollegeController;
import com.logical.examportal.service.CollegeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CollegeControllerImpl implements CollegeController {

    @Autowired
    CollegeService collegeService;
    private final Logger logger = LoggerFactory.getLogger(CollegeControllerImpl.class);

    @Override
    public ResponseEntity<?> getById(Long collegeId) {
        try {
            return collegeService.getById(collegeId);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(
                    new MessageResponse(false,
                            "Something went wrong...Don't worry we are figuring out what went wrong...!"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> deleteById(Long collegeId) {
        try {
            return collegeService.deleteById(collegeId);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(
                    new MessageResponse(false,
                            "Something went wrong...Don't worry we are figuring out what went wrong...!"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> create(College college) {
        try {
            return collegeService.create(college);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(
                    new MessageResponse(false,
                            "Something went wrong...Don't worry we are figuring out what went wrong...!"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> update(College college) {
        try {
            return collegeService.update(college);
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
            return collegeService.getAll();
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(
                    new MessageResponse(false,
                            "Something went wrong...Don't worry we are figuring out what went wrong...!"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getActiveByCity(Long cityId) {
        try {
            return collegeService.getActiveByCity(cityId);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(
                    new MessageResponse(false,
                            "Something went wrong...Don't worry we are figuring out what went wrong...!"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

package com.logical.examportal.service.impl;

import com.logical.examportal.entity.TermsCondition;
import com.logical.examportal.model.response.GenericResponse;
import com.logical.examportal.model.response.MessageResponse;
import com.logical.examportal.repository.TermsConditionRepository;
import com.logical.examportal.service.TermsConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TermsConditionServiceImpl implements TermsConditionService {
    @Autowired
    TermsConditionRepository termsConditionRepository;

    @Override
    public ResponseEntity<?> create(TermsCondition termsCondition) {
        termsCondition.setCurrentDateTime(LocalDateTime.now());
        termsConditionRepository.save(termsCondition);
        return new ResponseEntity<>( new MessageResponse(true, "Record created successfully."), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAll() {
        List<TermsCondition> examList =  termsConditionRepository.findAll();
        return new ResponseEntity<>( new GenericResponse<>(true, "Records get successfully", examList), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getById(Long examId) {
        Optional<TermsCondition> exam = termsConditionRepository.findById(examId);
        if(exam.isPresent()){
            return new ResponseEntity<>( new GenericResponse<>(true, "Records get successfully", exam), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>( new MessageResponse(false, "Record not found or invalid ID."), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> update(TermsCondition termsCondition) {
        boolean status = termsConditionRepository.existsById(termsCondition.getTermsConditionId());
        if(status){
            termsConditionRepository.save(termsCondition);
            return new ResponseEntity<>( new MessageResponse(true, "Record updated successfully."), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>( new MessageResponse(false, "Record not updated or invalid form data."), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> deleteById(Long examId) {
        boolean status = termsConditionRepository.existsById(examId);
        if(status){
            termsConditionRepository.deleteById(examId);
            return new ResponseEntity<>( new MessageResponse(true, "Record deleted successfully."), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>( new MessageResponse(false, "Record not deleted or invalid ID."), HttpStatus.NOT_FOUND);

        }
    }
}

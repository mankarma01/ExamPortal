package com.logical.examportal.service.impl;

import com.logical.examportal.entity.ExamRules;
import com.logical.examportal.model.response.GenericResponse;
import com.logical.examportal.model.response.MessageResponse;
import com.logical.examportal.repository.ExamRulesRepository;
import com.logical.examportal.service.ExamRulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ExamRulesServiceImpl implements ExamRulesService {

    @Autowired
    ExamRulesRepository examRulesRepository;

    @Override
    public ResponseEntity<?> create(ExamRules exam) {
        exam.setCurrentDateTime(LocalDateTime.now());
        examRulesRepository.save(exam);
        return new ResponseEntity<>( new MessageResponse(true, "Record created successfully."), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAll() {
        List<ExamRules> examList =  examRulesRepository.findAll();
        return new ResponseEntity<>( new GenericResponse<>(true, "Records get successfully", examList), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getById(Long examId) {
        Optional<ExamRules> exam = examRulesRepository.findById(examId);
        if(exam.isPresent()){
            return new ResponseEntity<>( new GenericResponse<>(true, "Records get successfully", exam), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>( new MessageResponse(false, "Record not found or invalid ID."), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> update(ExamRules exam) {
        boolean status = examRulesRepository.existsById(exam.getExamRulesId());
        if(status){
            exam.setCurrentDateTime(LocalDateTime.now());
            examRulesRepository.save(exam);
            return new ResponseEntity<>( new MessageResponse(true, "Record updated successfully."), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>( new MessageResponse(false, "Record not updated or invalid form data."), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> deleteById(Long examId) {
        boolean status = examRulesRepository.existsById(examId);
        if(status){
            examRulesRepository.deleteById(examId);
            return new ResponseEntity<>( new MessageResponse(true, "Record deleted successfully."), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>( new MessageResponse(false, "Record not deleted or invalid ID."), HttpStatus.OK);
        }
    }

}

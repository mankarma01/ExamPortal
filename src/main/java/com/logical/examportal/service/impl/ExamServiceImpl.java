package com.logical.examportal.service.impl;

import com.logical.examportal.entity.Exam;
import com.logical.examportal.model.response.GenericResponse;
import com.logical.examportal.model.response.MessageResponse;
import com.logical.examportal.repository.ExamRepository;
import com.logical.examportal.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ExamServiceImpl implements ExamService {

    @Autowired
    ExamRepository examRepository;

    @Override
    public ResponseEntity<?> create(Exam exam) {
        exam.setDateTime(LocalDateTime.now());
        examRepository.save(exam);
        return new ResponseEntity<>( new MessageResponse(true, "Record created successfully."), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAll() {
        List<Exam> examList =  examRepository.findAll();
        Collections.reverse(examList);
        return new ResponseEntity<>( new GenericResponse<>(true, "Records get successfully", examList), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getById(Long examId) {
        Optional<Exam> exam = examRepository.findById(examId);
        if(exam.isPresent()){
            return new ResponseEntity<>( new GenericResponse<>(true, "Records get successfully", exam), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>( new MessageResponse(false, "Record not found or invalid ID."), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> update(Exam exam) {
        boolean status = examRepository.existsById(exam.getExamId());
        if(status){
            examRepository.save(exam);
            return new ResponseEntity<>( new MessageResponse(true, "Record updated successfully."), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>( new MessageResponse(false, "Record not updated or invalid form data."), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> deleteById(Long examId) {
        boolean status = examRepository.existsById(examId);
        if(status){
            examRepository.deleteById(examId);
            return new ResponseEntity<>( new MessageResponse(true, "Record deleted successfully."), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>( new MessageResponse(false, "Record not deleted or invalid ID."), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> alreadyExistsCode(Long examId, String examCode) {

        if(examId==0){
            Optional<Exam> exam = examRepository.findByExamCode(examCode);
            if(exam.isPresent()){
                return new ResponseEntity<>( new MessageResponse(true, "Exam code already exist."), HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>( new MessageResponse(false, "Exam code not exist."), HttpStatus.OK);
            }
        }
        Optional<Exam> exam = examRepository.findByExamCode(examCode);
        if(exam.isPresent()){
            if(Objects.equals(exam.get().getExamId(), examId)){
                return new ResponseEntity<>( new MessageResponse(false, "Exam code same as examId exist."), HttpStatus.OK);
            }else{
                return new ResponseEntity<>( new MessageResponse(true, "Exam code already exist."), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>( new MessageResponse(false, "Exam code not exist."), HttpStatus.OK);
    }

}

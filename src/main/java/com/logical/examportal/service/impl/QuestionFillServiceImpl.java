package com.logical.examportal.service.impl;

import com.logical.examportal.entity.QuestionFill;
import com.logical.examportal.entity.Result;
import com.logical.examportal.model.response.GenericResponse;
import com.logical.examportal.model.response.MessageResponse;
import com.logical.examportal.repository.QuestionFillRepository;
import com.logical.examportal.repository.QuestionRepository;
import com.logical.examportal.repository.ResultRepository;
import com.logical.examportal.service.QuestionFillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionFillServiceImpl implements QuestionFillService {

    @Autowired
    QuestionFillRepository questionFillRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    ResultRepository resultRepository;


    @Override
    @Transactional
    public ResponseEntity<?> checkedQuestion(QuestionFill questionFill) {

        Optional<QuestionFill> questionFillSaved = questionFillRepository.findByQuestionIdAndExamIdAndResultId(questionFill.getQuestionId(), questionFill.getExamId(), questionFill.getResultId());
        Optional<Result> result = resultRepository.findById(questionFill.getResultId());
        if (result.isPresent() && result.get().getEndTime() != null && result.get().getEndTime().isBefore(LocalDateTime.now())) {
            return new ResponseEntity<>(new MessageResponse(false, "Exam is submitted."), HttpStatus.OK);
        }

        if(questionFillSaved.isPresent()){
            questionFillSaved.get().setSelectedOption(questionFill.getSelectedOption());
            questionFillRepository.save(questionFillSaved.get());
            return new ResponseEntity<>(new MessageResponse(true, "Answer updated successfully.") , HttpStatus.OK);
        }else {
            questionFillRepository.save(questionFill);
            return new ResponseEntity<>(new MessageResponse(true, "Answer inserted successfully.") , HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> getAllQuestionFill() {
        List<QuestionFill> questionFillList =  questionFillRepository.findAll();
        return new ResponseEntity<>( new GenericResponse<>(true, "Records get successfully", questionFillList), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAllQuestionFillByResultId(Long resultId) {
        List<QuestionFill> questionFillList =  questionFillRepository.findByResultId(resultId);
        return new ResponseEntity<>( new GenericResponse<>(true, "Records get successfully", questionFillList), HttpStatus.OK);
    }
}

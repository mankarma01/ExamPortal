package com.logical.examportal.service.impl;

import com.logical.examportal.entity.Question;
import com.logical.examportal.model.response.GenericResponse;
import com.logical.examportal.model.response.MessageResponse;
import com.logical.examportal.repository.QuestionRepository;
import com.logical.examportal.service.QuestionService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    QuestionRepository questionRepository;

    @Override
    public ResponseEntity<?> create(Question question) {
        questionRepository.save(question);
        return new ResponseEntity<>( new MessageResponse(true, "Record created successfully."), HttpStatus.OK);
    }

    public ResponseEntity<?> saveCSVQuestionData(MultipartFile file, Long examId, Character examSet) {

        //boolean exits = questionRepository.existsByExamId(examId);

/*        if(exits){
            return new ResponseEntity<>( new MessageResponse(false, "Exam question already exits."), HttpStatus.OK);
        }*/

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            List<Question> questionList = new ArrayList<>();
            for (CSVRecord record : csvParser) {
                Question question = new Question();
                question.setExamId(examId);
                question.setQuestion(record.get("question"));
                question.setOption1(record.get("option1"));
                question.setOption2(record.get("option2"));
                question.setOption3(record.get("option3"));
                question.setOption4(record.get("option4"));
                question.setCorrectAnswer(record.get("correctAnswer").charAt(0));
                question.setExamSet(examSet);
                questionList.add(question);
            }

            questionRepository.saveAll(questionList);
            return new ResponseEntity<>( new MessageResponse(true, "CSV file uploaded successfully."), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>( new MessageResponse(false, "Error parsing CSV file." + e.getMessage()), HttpStatus.OK);
            //throw new RuntimeException("Error parsing CSV file: " + e.getMessage());
        }
    }

    public ResponseEntity<?> reSaveCSVQuestionData(MultipartFile file, Long examId) {

        boolean exits = questionRepository.existsByExamId(examId);

        if(exits){
            boolean deleteStatus = questionRepository.deleteByExamId(examId);
            System.out.println("Questions delete successfully.");
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            List<Question> questionList = new ArrayList<>();
            for (CSVRecord record : csvParser) {
                Question question = new Question();
                question.setExamId(examId);
                question.setQuestion(record.get("question"));
                question.setOption1(record.get("option1"));
                question.setOption2(record.get("option2"));
                question.setOption3(record.get("option3"));
                question.setOption4(record.get("option4"));
                question.setCorrectAnswer(record.get("correctAnswer").charAt(0));
                questionList.add(question);
            }
            questionRepository.saveAll(questionList);
            return new ResponseEntity<>( new MessageResponse(true, "CSV file uploaded successfully."), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>( new MessageResponse(false, "Error parsing CSV file." + e.getMessage()), HttpStatus.OK);
            //throw new RuntimeException("Error parsing CSV file: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> createAll(ArrayList<Question> questionList) {
        for (Question question: questionList){
            questionRepository.save(question);
        }
        return new ResponseEntity<>( new MessageResponse(true, "Records created successfully."), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAll() {
        List<Question> questionList =  questionRepository.findAll();
        Collections.reverse(questionList);
        return new ResponseEntity<>( new GenericResponse<>(true, "Records get successfully", questionList), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getById(Long questionId) {
        Optional<Question> question = questionRepository.findById(questionId);
        if(question.isPresent()){
            return new ResponseEntity<>( new GenericResponse<>(true, "Record get successfully", question), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>( new MessageResponse(false, "Record not found or invalid ID."), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> getByExamId(Long examId) {
        List<Question> questionList = questionRepository.findByExamId(examId);
        return new ResponseEntity<>( new GenericResponse<>(true, "Records get successfully", questionList), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> update(Question question) {

        boolean status = questionRepository.existsById(question.getQuestionId());
        if(status){
            questionRepository.save(question);
            return new ResponseEntity<>( new MessageResponse(true, "Record updated successfully."), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>( new MessageResponse(false, "Record not updated or invalid form data."), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> deleteById(Long questionId) {
        boolean status = questionRepository.existsById(questionId);
        if(status){
            questionRepository.deleteById(questionId);
            return new ResponseEntity<>( new MessageResponse(true, "Record deleted successfully."), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>( new MessageResponse(false, "Record not deleted or invalid ID."), HttpStatus.NOT_FOUND);

        }
    }

    @Transactional
    @Override
    public ResponseEntity<?> deleteQuestionSet(Long examId, Character examSet) {

        questionRepository.deleteByExamIdAndExamSet(examId, examSet);
        return new ResponseEntity<>( new MessageResponse(true, "Records deleted successfully."), HttpStatus.OK);

    }
}

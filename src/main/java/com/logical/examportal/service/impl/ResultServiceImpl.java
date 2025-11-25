package com.logical.examportal.service.impl;

import com.logical.examportal.entity.Exam;
import com.logical.examportal.entity.Result;
import com.logical.examportal.entity.Student;
import com.logical.examportal.model.response.GenericResponse;
import com.logical.examportal.model.response.MessageResponse;
import com.logical.examportal.repository.*;
import com.logical.examportal.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ResultServiceImpl implements ResultService {

    @Autowired
    ResultRepository resultRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    ExamRepository examRepository;

    @Autowired
    QuestionFillRepository questionFillRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Override
    public ResponseEntity<?> create(Result result) {
        Optional<Exam> exam = examRepository.findById(result.getExamId());
        Optional<Student> student = studentRepository.findById(result.getStudentId());
        if(exam.isPresent()&&student.isPresent()){

            result.setExamTitle(exam.get().getTitle());
            result.setExamDescription(exam.get().getDescription());
            result.setExamDate(exam.get().getExamDate());
            result.setExamTotalTime(exam.get().getTotalTime());

            result.setExam(exam.get());
            result.setStudent(student.get());
            resultRepository.save(result);
            return new ResponseEntity<>( new MessageResponse(true, "Record created successfully."), HttpStatus.OK);
        }else {
            return new ResponseEntity<>( new MessageResponse(false, "Record created successfully."), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> update(Result result) {
        boolean status = resultRepository.existsById(result.getResultId());
        if(status){
            resultRepository.save(result);
            return new ResponseEntity<>( new MessageResponse(true, "Record updated successfully."), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>( new MessageResponse(false, "Record not updated or invalid form data."), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> getById(Long resultId) {

        Optional<Result> result = resultRepository.findById(resultId);

        if(result.isPresent()){
            Optional<Student> student = studentRepository.findById(result.get().getStudent().getStudentId());
            Optional<Exam> exam = examRepository.findById(result.get().getExam().getExamId());
            result.get().setStudent(student.get());
            result.get().setExam(exam.get());
            return new ResponseEntity<>( new GenericResponse<>(true, "Records get successfully", result), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>( new MessageResponse(false, "Record not found or invalid ID."), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> getAll() {
        List<Result> resultList =  resultRepository.findAll();
        for(Result cur_result: resultList){
            Optional<Student> student = studentRepository.findById(cur_result.getStudent().getStudentId());
            Optional<Exam> exam = examRepository.findById(cur_result.getExam().getExamId());
            cur_result.setStudent(student.get());
            cur_result.setExam(exam.get());
        }
        return new ResponseEntity<>( new GenericResponse<>(true, "Records get successfully", resultList), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<?> getAllByExamId(Long examId, String resultStatus, int minimumMarks, String examDate, Long collegeId) {

        List<Result> resultList = new ArrayList<>();

        if(!examDate.equalsIgnoreCase("")){
            resultList = switch (resultStatus) {
                case "all" ->
                        resultRepository.findByExamExamIdAndExamDateAndCorrectGreaterThanEqual(examId, LocalDate.parse(examDate), minimumMarks);
                case "complete" ->
                        resultRepository.findByExamExamIdAndExamDateAndIsActiveAndCorrectGreaterThanEqual(examId, LocalDate.parse(examDate), false, minimumMarks);
                case "running" ->
                        resultRepository.findByExamExamIdAndExamDateAndIsActiveAndCorrectGreaterThanEqual(examId, LocalDate.parse(examDate), true, minimumMarks);
                default -> resultList;
            };
        }else {

            resultList = switch (resultStatus) {
                case "all" -> resultRepository.findByExamExamIdAndCorrectGreaterThanEqual(examId, minimumMarks);
                case "complete" ->
                        resultRepository.findByExamExamIdAndIsActiveAndCorrectGreaterThanEqual(examId, false, minimumMarks);
                case "running" ->
                        resultRepository.findByExamExamIdAndIsActiveAndCorrectGreaterThanEqual(examId, true, minimumMarks);
                default -> resultList;
            };
        }

        if(collegeId>0){

            List<Result> resultListByCollege = resultList.stream()
                    .filter(curr -> curr.getStudent().getCollege().getCollegeId() == collegeId)
                    .collect(Collectors.toList());

            Collections.sort(resultListByCollege, new Comparator<Result>() {
                @Override
                public int compare(Result r1, Result r2) {
                    return Integer.compare( r2.getCorrect(),  r1.getCorrect()); // Descending order
                }
            });
            return new ResponseEntity<>( new GenericResponse<>(true, "Records get successfully", resultListByCollege), HttpStatus.OK);


        }

        Collections.sort(resultList, new Comparator<Result>() {
            @Override
            public int compare(Result r1, Result r2) {
                return Integer.compare( r2.getCorrect(),  r1.getCorrect()); // Descending order
            }
        });
        return new ResponseEntity<>( new GenericResponse<>(true, "Records get successfully", resultList), HttpStatus.OK);
    }


/*
    @Override
    public ResponseEntity<?> getAllByExamId(Long examId, String resultStatus, int minimumMarks, String examDate, Long collegeId, Long cityId) {

        if(cityId==-1 && collegeId>0){

            if(!examDate.equalsIgnoreCase("")){
                List<Result> studentList =  resultRepository.findByExamExamIdAndStudentCollegeCollegeIdAndExamDateAndCorrectGreaterThanEqual(examId ,collegeId, LocalDate.parse(examDate), minimumMarks);
                Collections.reverse(studentList);
                return new ResponseEntity<>( new GenericResponse<>(true, "Records get successfully", studentList), HttpStatus.OK);
            }

            List<Result> studentList =  resultRepository.findByExamExamIdAndStudentCollegeCollegeIdAndCorrectGreaterThanEqual(examId, collegeId, minimumMarks);
            Collections.reverse(studentList);
            return new ResponseEntity<>( new GenericResponse<>(true, "Records get successfully", studentList), HttpStatus.OK);
        }

        if(cityId==-1){

            if(!examDate.equalsIgnoreCase("")){
                List<Result> studentList =  resultRepository.findByExamExamIdAndExamDateAndCorrectGreaterThanEqual(examId ,LocalDate.parse(examDate), minimumMarks);
                Collections.reverse(studentList);
                return new ResponseEntity<>( new GenericResponse<>(true, "Records get successfully", studentList), HttpStatus.OK);
            }

            List<Result> studentList =  resultRepository.findByExamExamIdAndCorrectGreaterThanEqual( examId,minimumMarks);
            Collections.reverse(studentList);
            return new ResponseEntity<>( new GenericResponse<>(true, "Records get successfully", studentList), HttpStatus.OK);
        }

        if(cityId>0 && collegeId ==0){
            if(!examDate.equalsIgnoreCase("")){
                List<Result> studentList =  resultRepository.findByExamExamIdAndStudentCollegeCityCityIdAndExamDateAndCorrectGreaterThanEqual(examId,cityId, LocalDate.parse(examDate), minimumMarks);
                Collections.reverse(studentList);
                return new ResponseEntity<>( new GenericResponse<>(true, "Records get successfully", studentList), HttpStatus.OK);
            }

            List<Result> studentList =  resultRepository.findByExamExamIdAndStudentCollegeCityCityIdAndCorrectGreaterThanEqual(examId, cityId, minimumMarks);
            Collections.reverse(studentList);
            return new ResponseEntity<>( new GenericResponse<>(true, "Records get successfully", studentList), HttpStatus.OK);
        }

        if(cityId>0 && collegeId>0){

            if(!examDate.equalsIgnoreCase("")){
                List<Result> studentList =  resultRepository.findByExamExamIdAndCollegeCollegeIdAndExamDateAndCorrectGreaterThanEqual(examId, collegeId ,LocalDate.parse(examDate), minimumMarks);
                Collections.reverse(studentList);
                return new ResponseEntity<>( new GenericResponse<>(true, "Records get successfully", studentList), HttpStatus.OK);
            }
            List<Result> studentList =  resultRepository.findByExamExamIdAndCollegeCollegeIdAndCorrectGreaterThanEqual(examId, collegeId, minimumMarks);
            Collections.reverse(studentList);
            return new ResponseEntity<>( new GenericResponse<>(true, "Records get successfully", studentList), HttpStatus.OK);
        }

            //old code

        List<Result> resultList = new ArrayList<>();

        if(!examDate.equalsIgnoreCase("")){
            resultList = switch (resultStatus) {
                case "all" ->
                        resultRepository.findByExamExamIdAndExamDateAndCorrectGreaterThanEqual(examId, LocalDate.parse(examDate), minimumMarks);
                case "complete" ->
                        resultRepository.findByExamExamIdAndExamDateAndIsActiveAndCorrectGreaterThanEqual(examId, LocalDate.parse(examDate), false, minimumMarks);
                case "running" ->
                        resultRepository.findByExamExamIdAndExamDateAndIsActiveAndCorrectGreaterThanEqual(examId, LocalDate.parse(examDate), true, minimumMarks);
                default -> resultList;
            };
        }else {

            resultList = switch (resultStatus) {
                case "all" -> resultRepository.findByExamExamIdAndCorrectGreaterThanEqual(examId, minimumMarks);
                case "complete" ->
                        resultRepository.findByExamExamIdAndIsActiveAndCorrectGreaterThanEqual(examId, false, minimumMarks);
                case "running" ->
                        resultRepository.findByExamExamIdAndIsActiveAndCorrectGreaterThanEqual(examId, true, minimumMarks);
                default -> resultList;
            };
        }

        if(collegeId>0){

            List<Result> resultListByCollege = resultList.stream()
                    .filter(curr -> curr.getStudent().getCollege().getCollegeId() == collegeId)
                    .collect(Collectors.toList());

            Collections.sort(resultListByCollege, new Comparator<Result>() {
                @Override
                public int compare(Result r1, Result r2) {
                    return Integer.compare( r2.getCorrect(),  r1.getCorrect()); // Descending order
                }
            });
            return new ResponseEntity<>( new GenericResponse<>(true, "Records get successfully", resultListByCollege), HttpStatus.OK);


        }

        Collections.sort(resultList, new Comparator<Result>() {
            @Override
            public int compare(Result r1, Result r2) {
                return Integer.compare( r2.getCorrect(),  r1.getCorrect()); // Descending order
            }
        });
        return new ResponseEntity<>( new GenericResponse<>(true, "Records get successfully", resultList), HttpStatus.OK);
    }

*/

    @Override
    public ResponseEntity<?> createExam(Long studentId, Long examId) {


        if(!(examId >0)){
            return new ResponseEntity<>( new MessageResponse(false, "Exam session not created or exam details are invalid."), HttpStatus.NOT_FOUND);
        }
        if(!(studentId >0)){
            return new ResponseEntity<>( new MessageResponse(false, "Exam session not created or students details are invalid."), HttpStatus.NOT_FOUND);
        }
        Student student = studentRepository.findById(studentId).get();
        Exam exam = examRepository.findById(examId).get();

        Result activeResult = resultRepository.findByStudentStudentIdAndIsActive(studentId, true);

        if(!(activeResult ==null)){
            return new ResponseEntity<>( new GenericResponse<>(true, "Exam session already started.", activeResult.getResultId()), HttpStatus.OK);
        }

        Result result = new Result();
        result.setStudent(student);
        result.setExam(exam);
        result.setStartTime(LocalDateTime.now());
        result.setIsActive(true);
        result.setExamTitle(exam.getTitle());
        result.setExamDescription(exam.getDescription());
        result.setExamDate(exam.getExamDate());
        result.setExamTotalTime(exam.getTotalTime());
        result.setEndTime(result.getStartTime().plusMinutes(exam.getTotalTime()));
        Result savedResult =  resultRepository.save(result);
        return new ResponseEntity<>( new GenericResponse<>(true, "Exam session created successfully.", savedResult.getResultId()), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteById(Long resultId) {
        boolean status = resultRepository.existsById(resultId);
        if(status){
            questionFillRepository.deleteByResultId(resultId);
            resultRepository.deleteById(resultId);
            return new ResponseEntity<>( new MessageResponse(true, "Record deleted successfully."), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>( new MessageResponse(false, "Record not deleted or invalid ID."), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> changeStatusById(Long resultId) {
        Optional<Result> resultOptional = resultRepository.findById(resultId);
        if(resultOptional.isPresent()){
            resultOptional.get().setIsActive(true);
            resultOptional.get().setSubmitDate(null);
            resultOptional.get().setCorrect(0);
            resultOptional.get().setTotalMarks(0);
            resultOptional.get().setMaxAttempt(0);
            resultRepository.save(resultOptional.get());
            return new ResponseEntity<>( new MessageResponse(true, "Record status updated successfully."), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>( new MessageResponse(false, "Record status not updated or invalid ID."), HttpStatus.NOT_FOUND);
        }
    }
}

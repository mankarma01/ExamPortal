package com.logical.examportal.service.impl;

import com.logical.examportal.dto.ResultIdExamSetDto;
import com.logical.examportal.dto.ScoreDetails;
import com.logical.examportal.entity.Exam;
import com.logical.examportal.entity.Question;
import com.logical.examportal.entity.QuestionFill;
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

        if (exam.isPresent() && student.isPresent()) {

            Exam examObj = exam.get();

            // Store exam info in result
            result.setExamTitle(examObj.getTitle());
            result.setExamDescription(examObj.getDescription());
            result.setExamDate(examObj.getExamDate());
            result.setExamTotalTime(examObj.getTotalTime());

            result.setExam(examObj);
            result.setStudent(student.get());

            // -------------------------
            // Assign examSet automatically
            // -------------------------
            Character examSet = null;
            if ("Yes".equalsIgnoreCase(examObj.getSetAisActive())) examSet = 'A';
            if ("Yes".equalsIgnoreCase(examObj.getSetBisActive())) examSet = 'B';
            if ("Yes".equalsIgnoreCase(examObj.getSetCisActive())) examSet = 'C';

            result.setExamSet(examSet);

            // default maxAttempt
            result.setMaxAttempt(1);

            // Save
            resultRepository.save(result);

            // -------------------------
            // Build RESPONSE for frontend
//            -------------------------
            Map<String, Object> data = new HashMap<>();
            data.put("resultId", result.getResultId());
            data.put("examSet", result.getExamSet());
            data.put("maxAttempt", result.getMaxAttempt());
            data.put("examId", examObj.getExamId());
            data.put("studentId", student.get().getStudentId());

            Map<String, Object> response = new HashMap<>();
            response.put("result", true);
            response.put("data", data);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        // Not Found Response
        Map<String, Object> response = new HashMap<>();
        response.put("result", false);
        response.put("message", "Exam or Student not found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
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
    public ResponseEntity<?>createExam(Long studentId, Long examId) {


        if (!(examId > 0)) {
            return new ResponseEntity<>(new MessageResponse(false, "The exam session has not been created, or the exam details are invalid."), HttpStatus.OK);
        }
        if (!(studentId > 0)) {
            return new ResponseEntity<>(new MessageResponse(false, "The exam session has not been created, or the student's details are invalid."), HttpStatus.OK);
        }


        Student student = studentRepository.findById(studentId).get();
        Exam exam = examRepository.findById(examId).get();



        Result activeResult = resultRepository.findByStudentStudentIdAndIsActive(studentId, true);

        if (!(activeResult == null)) {
            ResultIdExamSetDto resultExamSet = new ResultIdExamSetDto(activeResult.getResultId(), activeResult.getExamSet(), activeResult.getMaxAttempt());
            return new ResponseEntity<>(new GenericResponse<>(true, "The exam session has already started.", resultExamSet), HttpStatus.OK);
        }

        Result alreadyInserted = resultRepository.findByStudentStudentIdAndExamExamId(studentId, examId);
        if (!(alreadyInserted == null)) {
            return new ResponseEntity<>(new MessageResponse(false, "The exam has already been submitted. You can now view the result."), HttpStatus.OK);
        }

        Result result = new Result();
        result.setStudent(student);
        result.setExam(exam);
        result.setStartTime(LocalDateTime.now());
        result.setIsActive(true);
        result.setMaxAttempt(0);

        Random random = new Random();

        /*
        Randomly select 'A', 'B', or 'C'
        char examSet = (char) ('A' + random.nextInt(3));
        result.setExamSet(examSet);
        */

        List<Character> activeSets = new ArrayList<>();

        // Check which sets are active
        if ("Yes".equalsIgnoreCase(exam.getSetAisActive())) {
            activeSets.add('A');
        }
        if ("Yes".equalsIgnoreCase(exam.getSetBisActive())) {
            activeSets.add('B');
        }
        if ("Yes".equalsIgnoreCase(exam.getSetCisActive())) {
            activeSets.add('C');
        }

        // If there are active sets, randomly select one
        if (!activeSets.isEmpty()) {
            char examSet = activeSets.get(random.nextInt(activeSets.size()));
            result.setExamSet(examSet); // Set the randomly chosen active set
        } else {
            result.setExamSet('-'); // Default if no active set is found
        }

        result.setExamTitle(exam.getTitle());
        result.setExamDescription(exam.getDescription());
        result.setExamDate(exam.getExamDate());
        result.setExamTotalTime(exam.getTotalTime());
        result.setEndTime(result.getStartTime().plusMinutes(exam.getTotalTime()));
        Result savedResult = resultRepository.save(result);
        ResultIdExamSetDto resultExamSet = new ResultIdExamSetDto(savedResult.getResultId(), savedResult.getExamSet(), savedResult.getMaxAttempt());
        return new ResponseEntity<>(new GenericResponse<>(true, "The exam session has been created successfully.", resultExamSet), HttpStatus.OK);
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
    @Override
    public ResponseEntity<?> startExam(String email) {
        Optional<Student> student = studentRepository.findByEmail(email);

        if (student.isPresent()) {
            return new ResponseEntity<>(new GenericResponse<>(true, "Student details have been found successfully.", student.get().getStudentId()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new MessageResponse(false, "Student details not found"), HttpStatus.OK);

        }
    }
    
//    private void submitExam(Result result) {
//
//        Optional<Student> student = studentRepository.findById(result.getStudent().getStudentId());
//        List<QuestionFill> questionFillList = questionFillRepository.findByResultId(result.getResultId());
//        List<Question> questionList = questionRepository.findByExamIdAndExamSet(result.getExam().getExamId(), result.getExamSet());
//        int score = 0;
//        for (QuestionFill curQuestionFill : questionFillList) {
//            Optional<Question> question = questionRepository.findById(curQuestionFill.getQuestionId());
//            if (question.isPresent() && question.get().getCorrectAnswer().equals(curQuestionFill.getSelectedOption())) {
//                score = score + 1;
//            }
//        }
//        result.setTotalMarks( questionList.size());
//        result.setCorrect(score);
//        result.setIsActive(false);
//        result.setSubmitDate(LocalDateTime.now());
//        resultRepository.save(result);
//    }
      
    @Override
    public ResponseEntity<?> submitExam(Long resultId) {
        Optional<Result> result = resultRepository.findById(resultId);

        if (result.isPresent()){
            Optional<Student > student = studentRepository.findById(result.get().getStudent().getStudentId());

            if(!result.get().getIsActive()){
                return new ResponseEntity<>( new GenericResponse<>(true, "Exam submitted successfully.", student.get().getEmail()), HttpStatus.OK);
            }

            List<QuestionFill> questionFillList = questionFillRepository.findByResultId(result.get().getResultId());
            List<Question> questionList = questionRepository.findByExamIdAndExamSet(result.get().getExam().getExamId(), result.get().getExamSet());
            int score = 0;
            for(QuestionFill curQuestionFill : questionFillList){
                Optional<Question> question = questionRepository.findById(curQuestionFill.getQuestionId());
                if(question.isPresent() && question.get().getCorrectAnswer().equals(curQuestionFill.getSelectedOption())){
                    score = score + 1;
                }
            }
            result.get().setTotalMarks( questionList.size());
            result.get().setCorrect(score);
            result.get().setIsActive(false);
            result.get().setSubmitDate(LocalDateTime.now());
            resultRepository.save(result.get());
            return new ResponseEntity<>( new GenericResponse<>(true, "Exam submitted successfully.", student.get().getEmail()), HttpStatus.OK);
        }
        return new ResponseEntity<>( new MessageResponse(true, "Result not found or invalid result ID."), HttpStatus.OK);

    }
    @Override
    public ResponseEntity<?> viewScore(String email) {

        Optional<Student> student = studentRepository.findByEmail(email);

        if (student.isPresent()) {

            List<Result> resultList = resultRepository.findByStudentStudentId(student.get().getStudentId());
            if (resultList.isEmpty()) {
                return new ResponseEntity<>(new MessageResponse(false, "Exam result not found."), HttpStatus.OK);
            }

            List<ScoreDetails> scoreDetailsList = new ArrayList<>();

            for (Result result : resultList) {

                if(!result.getIsActive()){

                ScoreDetails sd = new ScoreDetails();

                List<QuestionFill> questionFillList = questionFillRepository.findByResultId(result.getResultId());
                int score = 0;
                for (QuestionFill curQuestionFill : questionFillList) {
                    Optional<Question> question = questionRepository.findById(curQuestionFill.getQuestionId());

                    if (question.isPresent() && question.get().getCorrectAnswer().equals(curQuestionFill.getSelectedOption())) {
                        score = score + 1;
                    }
                }
                sd.setStudentName(student.get().getName());
                sd.setFatherName(student.get().getFatherName());
                sd.setCourse(student.get().getCourse());
                sd.setBranch(student.get().getBranch());
                sd.setCollegeName(student.get().getCollege().getCollegeName());
                sd.setEmail(student.get().getEmail());
                sd.setEnrollmentId(student.get().getEnrollmentId());
                sd.setScore(score);
                sd.setExamTitle(result.getExamTitle());
                sd.setExamDate(result.getExamDate());
                scoreDetailsList.add(sd);
                }
            }

            if (scoreDetailsList.isEmpty()) {
                return new ResponseEntity<>(new MessageResponse(false, "Exam result not found or exam not submitted."), HttpStatus.OK);
            }
            return new ResponseEntity<>(new GenericResponse<>(true, "Score retrieved successfully", scoreDetailsList), HttpStatus.OK);
        }

        return new ResponseEntity<>(new MessageResponse(false, "Student record not found or invalid email."), HttpStatus.OK);

    }
    
    @Override
    public ResponseEntity<?> maxAttempt(Long resultId) {
        Optional<Result> result = resultRepository.findById(resultId);
        if(result.isPresent()){
            result.get().setMaxAttempt(result.get().getMaxAttempt()+1);
            resultRepository.save(result.get());
            return new ResponseEntity<>( new MessageResponse(true, "Exam attempt number increase successfully."), HttpStatus.OK);
        }
        return new ResponseEntity<>( new MessageResponse(false, "Result not found or invalid result ID."), HttpStatus.OK);
    }

}

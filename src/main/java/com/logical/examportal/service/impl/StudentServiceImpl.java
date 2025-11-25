package com.logical.examportal.service.impl;

import com.logical.examportal.dto.StudentResultDto;
import com.logical.examportal.entity.Exam;
import com.logical.examportal.entity.Result;
import com.logical.examportal.entity.Student;
import com.logical.examportal.model.response.GenericResponse;
import com.logical.examportal.model.response.MessageResponse;
import com.logical.examportal.model.response.StudentResponse;
import com.logical.examportal.repository.ExamRepository;
import com.logical.examportal.repository.ResultRepository;
import com.logical.examportal.repository.StudentRepository;
import com.logical.examportal.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    ResultRepository resultRepository;

    @Autowired
    ExamRepository examRepository;

    @Override
    public ResponseEntity<?> create(Student student) {
        student.setDateTime(LocalDateTime.now());
        studentRepository.save(student);
        return new ResponseEntity<>( new MessageResponse(true, "Record created successfully."), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAll() {
        List<Student> studentList =  studentRepository.findAll();
        Collections.reverse(studentList);
        return new ResponseEntity<>( new GenericResponse<>(true, "Records get successfully", studentList), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getByCollegeId(Long collegeId) {
        if(collegeId==-1){
            List<Student> studentList =  studentRepository.findAll();
            Collections.reverse(studentList);
            return new ResponseEntity<>( new GenericResponse<>(true, "Records get successfully", studentList), HttpStatus.OK);
        }
        List<Student> studentList =  studentRepository.findByCollegeCollegeId(collegeId);
        Collections.reverse(studentList);
        return new ResponseEntity<>( new GenericResponse<>(true, "Records get successfully", studentList), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getByCityCollege(Long cityId, Long collegeId) {

        if(cityId==-1 && collegeId>0){
            List<Student> studentList =  studentRepository.findByCollegeCollegeId(collegeId);
            Collections.reverse(studentList);
            return new ResponseEntity<>( new GenericResponse<>(true, "Records get successfully", studentList), HttpStatus.OK);
        }

        if(cityId==-1){
            List<Student> studentList =  studentRepository.findAll();
            Collections.reverse(studentList);
            return new ResponseEntity<>( new GenericResponse<>(true, "Records get successfully", studentList), HttpStatus.OK);
        }

        if(cityId>0 && collegeId ==0){
            List<Student> studentList =  studentRepository.findByCollegeCityCityId(cityId);
            Collections.reverse(studentList);
            return new ResponseEntity<>( new GenericResponse<>(true, "Records get successfully", studentList), HttpStatus.OK);
        }

        if(cityId>0 && collegeId>0){
            List<Student> studentList =  studentRepository.findByCollegeCityCityIdAndCollegeCollegeId(cityId, collegeId);
            Collections.reverse(studentList);
            return new ResponseEntity<>( new GenericResponse<>(true, "Records get successfully", studentList), HttpStatus.OK);

        }

        return new ResponseEntity<>( new GenericResponse<>(true, "Records get successfully", null), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<?> getById(Long internId) {
        Optional<Student> intern = studentRepository.findById(internId);
        if(intern.isPresent()){
            return new ResponseEntity<>( new GenericResponse<>(true, "Records get successfully", intern), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>( new MessageResponse(false, "Record not found or invalid ID."), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> update(Student student) {

        boolean status = studentRepository.existsById(student.getStudentId());
        if(status){
            studentRepository.save(student);
            return new ResponseEntity<>( new MessageResponse(true, "Record updated successfully."), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>( new MessageResponse(false, "Record not updated or invalid form data."), HttpStatus.NOT_FOUND);

        }
    }

    @Override
    public ResponseEntity<?> deleteById(Long internId) {
        boolean status = studentRepository.existsById(internId);
        if(status){
            studentRepository.deleteById(internId);
            return new ResponseEntity<>( new MessageResponse(true, "Record deleted successfully."), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>( new MessageResponse(false, "Record not deleted or invalid ID."), HttpStatus.NOT_FOUND);

        }
    }

    @Override
    public ResponseEntity<?> login(String username, String password) {

        Optional<Student> student = studentRepository.findByEmailAndPassword(username, password);

        if(student.isPresent()){

            List<Result> resultList = resultRepository.findByStudentStudentId(student.get().getStudentId());

            for(Result result : resultList){
                if(result.getIsActive()){
                    System.out.println("Student login successfully.\n Your exam is already started.");
                    return new ResponseEntity<>( new StudentResponse<>(true, "Student login successfully.\n Your exam is already started.","examActive", result), HttpStatus.OK);
                }
            }

            Exam exam = examRepository.findLatestActive(true);

            if(resultList.isEmpty()&& exam==null){
                StudentResultDto srDto = new StudentResultDto();
                srDto.setStudent(student.get());
                srDto.setResultList(resultList);
                System.out.println("Student login successfully.\n No active Exam and Score found.");
                return new ResponseEntity<>( new StudentResponse<>(true, "Student login successfully.\n No active Exam.","noActive", student), HttpStatus.OK);

            }

            if(!resultList.isEmpty()){
                StudentResultDto srDto = new StudentResultDto();
                srDto.setStudent(student.get());
                srDto.setResultList(resultList);
                System.out.println("Student login successfully.\n View your score.");
                return new ResponseEntity<>( new StudentResponse<>(true, "Student login successfully.\n View your score.","viewScore", student), HttpStatus.OK);

            }

            System.out.println("Student login successfully.");
            return new ResponseEntity<>( new StudentResponse<>(true, "Login successfully","disclaimer", student), HttpStatus.OK);
        }
        System.out.println("Authorization failed.");
        return new ResponseEntity<>(new MessageResponse(false, "login failed username or password is incorrect."), HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<?> takeExam(String username) {

        Optional<Student> student = studentRepository.findByEmail(username);

        if(student.isPresent()){

            List<Result> resultList = resultRepository.findByStudentStudentId(student.get().getStudentId());



            for(Result result : resultList){
                if(result.getIsActive()){
                    System.out.println("Student login successfully.\n Your exam is already started.");
                    return new ResponseEntity<>( new StudentResponse<>(true, "Student login successfully.\n Your exam is already started.","examActive", result), HttpStatus.OK);
                }
            }

            Exam exam = examRepository.findLatestActive(true);

            if(resultList.isEmpty()&& exam==null){
                StudentResultDto srDto = new StudentResultDto();
                srDto.setStudent(student.get());
                srDto.setResultList(resultList);
                System.out.println("Student login successfully.\n No active Exam and Score found.");
                return new ResponseEntity<>( new StudentResponse<>(true, "Student login successfully.\n No active Exam.","noActive", student), HttpStatus.OK);

            }

            if(!resultList.isEmpty()){
                StudentResultDto srDto = new StudentResultDto();
                srDto.setStudent(student.get());
                srDto.setResultList(resultList);
                System.out.println("Student login successfully.\n View your score.");
                return new ResponseEntity<>( new StudentResponse<>(true, "Student login successfully.\n View your score.","viewScore", student), HttpStatus.OK);

            }

            System.out.println("Student login successfully.");
            return new ResponseEntity<>( new StudentResponse<>(true, "Login successfully","disclaimer", student), HttpStatus.OK);
        }
        System.out.println("Authorization failed.");
        return new ResponseEntity<>(new MessageResponse(false, "login failed username or password is incorrect."), HttpStatus.NOT_FOUND);
    }
}

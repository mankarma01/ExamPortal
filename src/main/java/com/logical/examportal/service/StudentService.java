package com.logical.examportal.service;


import com.logical.examportal.entity.Student;
import org.springframework.http.ResponseEntity;

public interface StudentService {

    ResponseEntity<?> create(Student student);
    ResponseEntity<?> getAll();
    ResponseEntity<?> getByCollegeId(Long collegeId);
    ResponseEntity<?> getByCityCollege(Long cityId, Long collegeId);
    ResponseEntity<?> getById(Long studentId);
    ResponseEntity<?> update(Student student);
    ResponseEntity<?> deleteById(Long studentId);

    ResponseEntity<?> login(String username, String password);
    ResponseEntity<?> takeExam(String username);
}

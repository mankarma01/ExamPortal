package com.logical.examportal.service;

import com.logical.examportal.entity.College;
import org.springframework.http.ResponseEntity;

public interface CollegeService {

    ResponseEntity<?> create(College college);
    ResponseEntity<?> getAll();
    ResponseEntity<?> getById(Long collegeId);
    ResponseEntity<?> update(College college);
    ResponseEntity<?> getActiveByCity(Long cityId);
    ResponseEntity<?> deleteById(Long collegeId);

}

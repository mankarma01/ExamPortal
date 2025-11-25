package com.logical.examportal.service.impl;

import com.logical.examportal.dto.DashBoardDto;
import com.logical.examportal.model.response.GenericResponse;
import com.logical.examportal.repository.*;
import com.logical.examportal.service.DashBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DashBoardServiceImpl implements DashBoardService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    ExamRepository examRepository;

    @Autowired
    CollegeRepository collegeRepository;

    @Autowired
    CityRepository cityRepository;


    @Override
    public ResponseEntity<?> getDashBoardInfo() {
        DashBoardDto dsb = new DashBoardDto();
        dsb.setTotalStudent(studentRepository.count());
        dsb.setTotalExam(examRepository.count());
        dsb.setTotalCity(cityRepository.count());
        dsb.setTotalCollege(collegeRepository.count());
        return new ResponseEntity<>( new GenericResponse<>(true, "Dashboard records get successfully", dsb), HttpStatus.OK);
    }
}

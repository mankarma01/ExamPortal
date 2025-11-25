package com.logical.examportal.service.impl;

import com.logical.examportal.entity.City;
import com.logical.examportal.entity.College;
import com.logical.examportal.model.response.GenericResponse;
import com.logical.examportal.model.response.MessageResponse;
import com.logical.examportal.repository.CityRepository;
import com.logical.examportal.repository.CollegeRepository;
import com.logical.examportal.service.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CollegeServiceImpl implements CollegeService {

    @Autowired
    CollegeRepository collegeRepository;

    @Autowired
    CityRepository cityRepository;

    @Override
    public ResponseEntity<?> create(College college) {
        college.setDateTime(LocalDateTime.now());
        Optional<City> city = cityRepository.findById(college.getCityId());
        city.ifPresent(college::setCity);
        college.setIsActive(true);
        collegeRepository.save(college);
        return new ResponseEntity<>( new MessageResponse(true, "Record created successfully."), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAll() {
        List<College> collegeList =  collegeRepository.findAll();
        Collections.reverse(collegeList);
        return new ResponseEntity<>( new GenericResponse<>(true, "Records get successfully", collegeList), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getById(Long examId) {
        Optional<College> exam = collegeRepository.findById(examId);
        if(exam.isPresent()){
            return new ResponseEntity<>( new GenericResponse<>(true, "Records get successfully", exam), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>( new MessageResponse(false, "Record not found or invalid ID."), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> update(College exam) {
        boolean status = collegeRepository.existsById(exam.getCollegeId());
        Optional<City> city = cityRepository.findById(exam.getCityId());
        city.ifPresent(exam::setCity);
        if(status){
            collegeRepository.save(exam);
            return new ResponseEntity<>( new MessageResponse(true, "Record updated successfully."), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>( new MessageResponse(false, "Record not updated or invalid form data."), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> deleteById(Long examId) {
        boolean status = collegeRepository.existsById(examId);
        if(status){
            collegeRepository.deleteById(examId);
            return new ResponseEntity<>( new MessageResponse(true, "Record deleted successfully."), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>( new MessageResponse(false, "Record not deleted or invalid ID."), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> getActiveByCity(Long cityId) {
        if(cityId==-1){
            List<College> collegeList =  collegeRepository.findAll();
            return new ResponseEntity<>( new GenericResponse<>(true, "Active Records get successfully", collegeList), HttpStatus.OK);
        }
        List<College> collegeList =  collegeRepository.findByCityCityId(cityId);
        return new ResponseEntity<>( new GenericResponse<>(true, "Active Records get successfully", collegeList), HttpStatus.OK);
    }

}

package com.logical.examportal.service.impl;

import com.logical.examportal.entity.City;
import com.logical.examportal.model.response.GenericResponse;
import com.logical.examportal.model.response.MessageResponse;
import com.logical.examportal.repository.CityRepository;
import com.logical.examportal.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    CityRepository cityRepository;

    @Override
    public ResponseEntity<?> create(City city) {
        city.setDateTime(LocalDateTime.now());
        city.setIsActive(true);
        cityRepository.save(city);
        return new ResponseEntity<>( new MessageResponse(true, "Record created successfully."), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAll() {
        List<City> collegeList =  cityRepository.findAll();
        Collections.reverse(collegeList);
        return new ResponseEntity<>( new GenericResponse<>(true, "Records get successfully", collegeList), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getById(Long cityId) {
        Optional<City> city = cityRepository.findById(cityId);
        if(city.isPresent()){
            return new ResponseEntity<>( new GenericResponse<>(true, "Records get successfully", city), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>( new MessageResponse(false, "Record not found or invalid ID."), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> update(City city) {
        boolean status = cityRepository.existsById(city.getCityId());
        if(status){
            cityRepository.save(city);
            return new ResponseEntity<>( new MessageResponse(true, "Record updated successfully."), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>( new MessageResponse(false, "Record not updated or invalid form data."), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> deleteById(Long cityId) {
        boolean status = cityRepository.existsById(cityId);
        if(status){
            cityRepository.deleteById(cityId);
            return new ResponseEntity<>( new MessageResponse(true, "Record deleted successfully."), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>( new MessageResponse(false, "Record not deleted or invalid ID."), HttpStatus.OK);
        }
    }

}

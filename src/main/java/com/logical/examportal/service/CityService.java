package com.logical.examportal.service;

import com.logical.examportal.entity.City;
import org.springframework.http.ResponseEntity;

public interface CityService {

    ResponseEntity<?> create(City city);
    ResponseEntity<?> getAll();
    ResponseEntity<?> getById(Long cityId);
    ResponseEntity<?> update(City city);
    ResponseEntity<?> deleteById(Long cityId);

}

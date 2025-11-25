package com.logical.examportal.restcontroller;

import com.logical.examportal.entity.City;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/cityAPI")
public interface CityController {

    @PostMapping("/create")
    ResponseEntity<?> create(@RequestBody City city);

    @PutMapping("/update")
    ResponseEntity<?> update(@RequestBody City city);

    @GetMapping("/getAll")
    ResponseEntity<?> getAll();

    @GetMapping("/get/{cityId}")
    ResponseEntity<?> getById(@PathVariable("cityId") Long cityId);

    @DeleteMapping("/delete/{cityId}")
    ResponseEntity<?> deleteById(@PathVariable("cityId") Long cityId);
}

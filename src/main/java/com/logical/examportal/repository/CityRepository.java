package com.logical.examportal.repository;

import com.logical.examportal.entity.City;
import com.logical.examportal.entity.College;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {


}

package com.logical.examportal.repository;

import com.logical.examportal.entity.College;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollegeRepository extends JpaRepository<College, Long> {

    List<College> findByCityCityId(Long cityId);
}

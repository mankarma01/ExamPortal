package com.logical.examportal.repository;

import com.logical.examportal.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByEmailAndPassword(String username, String password);
    Optional<Student> findByEmail(String username);
    List<Student> findByCollegeCollegeId(Long collegeId);
    List<Student> findByCollegeCityCityId(Long cityId);
    List<Student> findByCollegeCityCityIdAndCollegeCollegeId(Long cityId, Long collegeId);

}

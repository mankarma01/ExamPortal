package com.logical.examportal.repository;

import com.logical.examportal.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {

    List<Result> findByExamExamId(Long examId);
    List<Result> findByExamExamIdAndCorrectGreaterThanEqual(Long examId, int marks);
    List<Result> findByExamExamIdAndIsActiveAndCorrectGreaterThanEqual(Long examId, Boolean isActive, int marks);

    List<Result> findByExamExamIdAndExamDateAndCorrectGreaterThanEqual(Long examId, LocalDate examDate, int marks);
    List<Result> findByExamExamIdAndExamDateAndIsActiveAndCorrectGreaterThanEqual(Long examId,LocalDate examDate,  Boolean isActive, int marks);

    List<Result> findByStudentStudentId(Long studentId);
    Result findByStudentStudentIdAndIsActive(Long studentId, boolean status);

/*
    List<Result> findByExamExamIdAndStudentCollegeCollegeIdAndExamDateAndCorrectGreaterThanEqual( Long examId,Long collegeId , LocalDate examDate, int marks);
    List<Result> findByExamExamIdAndStudentCollegeCollegeIdAndCorrectGreaterThanEqual(Long examId,Long collegeId , int marks);
    List<Result> findByExamExamIdAndStudentCollegeCityCityIdAndCorrectGreaterThanEqual(Long examId, Long cityId , int marks);
    List<Result> findByExamExamIdAndStudentCollegeCityCityIdAndExamDateAndCorrectGreaterThanEqual(Long examId,Long cityId, LocalDate examDate, int marks);
    List<Result> findByExamDateAndCorrectGreaterThanEqual(LocalDate examDate , int marks);
    List<Result> findByCorrectGreaterThanEqual(int marks);

    List<Result> findByExamExamIdAndCollegeCollegeIdAndExamDateAndCorrectGreaterThanEqual(Long examId,Long collegeId ,LocalDate examDate , int marks);
    List<Result> findByExamExamIdAndCollegeCollegeIdAndCorrectGreaterThanEqual(Long examId, Long collegeId , int marks);*/


}

package com.logical.examportal.repository;

import com.logical.examportal.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ExamRepository extends JpaRepository<Exam, Long> {

    @Query(value = "SELECT * FROM exam e WHERE e.is_active = :isActive ORDER BY e.date_time DESC LIMIT 1", nativeQuery = true)
    Exam findLatestActive(boolean isActive);
    Optional<Exam> findByExamCode(String examCode);
}

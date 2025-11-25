package com.logical.examportal.repository;

import com.logical.examportal.entity.QuestionFill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionFillRepository extends JpaRepository<QuestionFill, Long> {
    Optional<QuestionFill> findByQuestionIdAndExamIdAndResultId(Long questionId, Long examId, Long resultId);
    List<QuestionFill> findByResultId(Long resultId);
    void deleteByResultId(Long resultId);
}

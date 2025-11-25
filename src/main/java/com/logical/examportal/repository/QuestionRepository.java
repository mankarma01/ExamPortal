package com.logical.examportal.repository;

import com.logical.examportal.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    boolean existsByExamId(Long examId);
    List<Question> findByExamId(Long examId);
    List<Question> findByExamIdAndExamSet(Long examId, Character examSet);

    boolean deleteByExamId(Long examId);
    void deleteByExamIdAndExamSet(Long examId, Character examSet);

}

package com.logical.examportal.repository;

import com.logical.examportal.entity.TermsCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TermsConditionRepository extends JpaRepository<TermsCondition, Long> {
}

package com.logical.examportal.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class ExamRules {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exam_rules_id")
    private Long examRulesId;

    @Column(columnDefinition = "TEXT")
    private String examRules;

    private LocalDateTime currentDateTime;

}

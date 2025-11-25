package com.logical.examportal.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class TermsCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "terms_condition_id")
    private Long termsConditionId;

    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    private LocalDateTime currentDateTime;

}

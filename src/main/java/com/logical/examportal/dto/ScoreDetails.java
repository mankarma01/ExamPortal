package com.logical.examportal.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ScoreDetails {

    private String studentName;
    private String fatherName;
    private String email;
    private String collegeName;
    private String enrollmentId;
    private String course;
    private String branch;
    private String examTitle;
    private LocalDate examDate;
    private int score;

}

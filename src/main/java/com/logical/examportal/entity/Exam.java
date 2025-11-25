package com.logical.examportal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exam_id")
    private Long examId;

   // 2) Disclaimer pages( All terms & Conditions, Total time, rules for attempting question and answer )


    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    private LocalDateTime dateTime;
    private LocalDate examDate;
    private Integer totalTime;
    private Boolean isActive = true;
    private String examCode;

    private String setAisActive = "No";     //Yes No
    private String setBisActive = "No";
    private String setCisActive = "No";

}

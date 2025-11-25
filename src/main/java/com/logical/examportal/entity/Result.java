package com.logical.examportal.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "result_id")
    private Long resultId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE,  CascadeType.DETACH})
    @JoinColumn(name = "student_id", referencedColumnName = "student_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH} )
    @JoinColumn(name = "exam_id", referencedColumnName = "exam_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Exam exam;

    private String examTitle;
    @Column(columnDefinition = "TEXT")
    private String examDescription;
    private LocalDate examDate;
    private Integer examTotalTime;
    private Character examSet;
    private Integer correct;
    private Integer totalMarks;
    private Integer wrong;
    private Integer skip;
    private Integer next;
    private Integer maxAttempt;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime submitDate;
    private Boolean isActive;

    @Transient
    private Long examId;
    @Transient
    private Long studentId;

}

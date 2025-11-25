package com.logical.examportal.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long studentId;

    private String name;
    private String fatherName;
    private String mobile;
    private String enrollmentId;
    private String email;
    private String password;
    private String collageName;
    private String course;
    private String branch;
    private String alternateMobile;
    private String grad;
    private LocalDateTime dateTime;
    private String address;
    private Float cgpa;
    private String technology;

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "collegeId", referencedColumnName = "collegeId")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private College college;

}

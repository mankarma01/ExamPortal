package com.logical.examportal.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionFill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fillQuestionId;

    private Long resultId;
    private Long examId;
    private Long studentId;
    private Long questionId;
    private Character selectedOption;

}

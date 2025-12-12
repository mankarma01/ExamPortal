package com.logical.examportal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultIdExamSetDto {
    private Long resultId;
    private Character examSet;
    private Integer maxAttempt;

}

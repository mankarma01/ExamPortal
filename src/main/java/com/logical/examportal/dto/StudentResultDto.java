package com.logical.examportal.dto;

import com.logical.examportal.entity.Result;
import com.logical.examportal.entity.Student;
import lombok.Data;

import java.util.List;

@Data
public class StudentResultDto {
    private Student student;
    private List<Result> resultList;
}

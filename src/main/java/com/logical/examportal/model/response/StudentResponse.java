package com.logical.examportal.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse<T> {
	 private boolean result;
	 private String message;
	 private String status;
	 private T data;
}

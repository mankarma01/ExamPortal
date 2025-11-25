package com.logical.examportal.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponse<T> {
	 private boolean result;
	 private String message;
	 private T data;
}

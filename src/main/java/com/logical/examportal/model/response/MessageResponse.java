package com.logical.examportal.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MessageResponse {
	public boolean result;
	public String message;

	public MessageResponse(boolean result, String message) {
		this.message = message;
		this.result = result;
	}
}
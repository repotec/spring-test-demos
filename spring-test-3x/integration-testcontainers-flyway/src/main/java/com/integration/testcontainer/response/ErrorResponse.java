package com.integration.testcontainer.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {
	private int statusCode;
	private String message;
	private LocalDateTime timestamp = LocalDateTime.now();
	private String url;
	private List<String> errors;
}

package com.integration.container.test.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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

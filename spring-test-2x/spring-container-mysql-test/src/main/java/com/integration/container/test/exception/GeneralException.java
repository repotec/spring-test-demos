package com.integration.container.test.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.integration.container.test.response.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GeneralException extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public final ResponseEntity<Object> generalHandleException(Exception ex, WebRequest request) {
		
		//jdbcTemplate.update("call LOG_EXCEPTIONS(?,?,?)", adminErrorResponse.getErrorCode(), adminErrorResponse.getErrorMessage(), request.getDescription(false));
		
		List<String> errors = new ArrayList<String>();
		errors.add(ex.getMessage());
		 
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
														"bad request",
														LocalDateTime.now(),
														request.getDescription(false),
														errors);
		
		ex.printStackTrace();
		return new ResponseEntity<Object>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR); 
	}


	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> errors = new ArrayList<String>();
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();


		for(FieldError fieldError : fieldErrors)
			errors.add(fieldError.getDefaultMessage());

		for(ObjectError globalError : globalErrors)
			errors.add(globalError.getDefaultMessage());

		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
														"bad request",
														LocalDateTime.now(),
														request.getDescription(false),
														errors);
		ex.getStackTrace();
		return new ResponseEntity<Object>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	
}

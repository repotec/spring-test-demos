package com.integration.container.test.exception;

public class PasswordWrongExcpetion extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public PasswordWrongExcpetion(String exception) {
		super(exception);
	}
	
	public PasswordWrongExcpetion(Throwable cause) {
		super(cause);
	} 
}

package com.mph.services.exceptions;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EmailAlreadyInUseException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EmailAlreadyInUseException() {
		super("This email address is already in use. Please choose another.");
	}

}
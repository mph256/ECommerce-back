package com.mph.services.exceptions;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PhoneAlreadyInUseException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PhoneAlreadyInUseException() {
		super("This phone number is already in use. Please choose another.");
	}

}
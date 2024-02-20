package com.mph.services.exceptions;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidCardNumberFormatException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidCardNumberFormatException() {
		super("Invalid card number format.");
	}

}
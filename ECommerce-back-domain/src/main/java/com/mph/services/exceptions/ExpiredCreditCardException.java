package com.mph.services.exceptions;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.GONE)
public class ExpiredCreditCardException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ExpiredCreditCardException() {
		super("Your credit card has expired.");
	}

}
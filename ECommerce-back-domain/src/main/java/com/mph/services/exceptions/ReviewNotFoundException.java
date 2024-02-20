package com.mph.services.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ReviewNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ReviewNotFoundException() {
		super("Review not found.");
	}

}
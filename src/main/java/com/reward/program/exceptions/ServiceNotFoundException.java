package com.reward.program.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ServiceNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public ServiceNotFoundException(String msg) {
		super(msg);
	}
}

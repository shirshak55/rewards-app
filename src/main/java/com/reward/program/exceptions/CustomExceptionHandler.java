package com.reward.program.exceptions;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@RestController
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	//General EXCEPTION
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest webRequest) {
		ApiError exceptionResponse = new ApiError();
		exceptionResponse.setMessage(ex.getMessage());

		// webRequest.getDescription(false);
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	//BAD REQUEST
	@ExceptionHandler(BadRequestException.class)
	ResponseEntity<Object> handleBadRequest(Exception ex, WebRequest request) {
		ApiError error1 = new ApiError();
		error1.setMessage(String.join(": ", "Bad Request:", ex.getMessage()));
		request.getDescription(false);
		return new ResponseEntity<Object>(error1, HttpStatus.NOT_FOUND);
	}

	//SERVICE NOT FOUND EXCEPTION
	@ExceptionHandler(ServiceNotFoundException.class)
	protected ResponseEntity<Object> handleEntityNotFound(Exception ex) {
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
		apiError.setMessage(ex.getMessage());
		apiError.setDebugMessage("");
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}

}

package com.helpCenter.user.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserGlobalExceptionHandler {

	@ExceptionHandler(UserAlreadyExist.class)
	public ResponseEntity<Response> userAlreadyExist(UserAlreadyExist alreadyExist) {
		String message = alreadyExist.getMessage();
		Response response = new Response(message);

		return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UserNotFound.class)
	public ResponseEntity<Response> userNotFound(UserNotFound userNotFound) {
		String message = userNotFound.getMessage();
		Response response = new Response(message);
		return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
	}
	

}

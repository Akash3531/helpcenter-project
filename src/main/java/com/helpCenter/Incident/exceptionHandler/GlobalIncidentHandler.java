package com.helpCenter.Incident.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@RestControllerAdvice
public class GlobalIncidentHandler {

	@ExceptionHandler(IncidentNotFoundException.class)
	public ResponseEntity<Response> IncidentNotFound(IncidentNotFoundException ex) {
		String message = ex.getMessage();
		Response response = new Response(message);
		return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MissingServletRequestPartException.class)
	public ResponseEntity<Response> missingPart(MissingServletRequestPartException ex)
	{
		String message = ex.getMessage();
		Response response = new Response(message);
		return new ResponseEntity<Response>(response,HttpStatus.BAD_REQUEST);
	}
}

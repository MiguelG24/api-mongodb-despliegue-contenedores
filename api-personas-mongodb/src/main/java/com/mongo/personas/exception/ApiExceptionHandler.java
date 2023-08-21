package com.mongo.personas.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestControllerAdvice
public class ApiExceptionHandler {

	@Autowired
	@SuppressWarnings("unused")
	private ObjectMapper objectMapper;
	
	@SuppressWarnings("rawtypes")
	@ExceptionHandler(PersonaNoEncontradaException.class)
	public HttpEntity notFound(PersonaNoEncontradaException exception) {
		
		HashMap<String, Object> body = new HashMap<>();		
		body.put("id", exception.getId());
		body.put("message", exception.getMessage());
		
		return ResponseEntity.status(NOT_FOUND).body(body);
	}
}

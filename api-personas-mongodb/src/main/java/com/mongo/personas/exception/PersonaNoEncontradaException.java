package com.mongo.personas.exception;

import lombok.Getter;

@Getter
@SuppressWarnings("serial")
public class PersonaNoEncontradaException extends RuntimeException{

	private final String id;
	
	public PersonaNoEncontradaException(String message, String id) {
		super(message);
		this.id = id;
	}
	
	public static PersonaNoEncontradaException from(String message, String id) {
		return new PersonaNoEncontradaException(message, id);
	}
	
}

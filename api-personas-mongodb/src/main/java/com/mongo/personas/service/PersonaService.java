package com.mongo.personas.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.mongo.personas.entity.Persona;
import com.mongo.personas.models.PersonaDto;
import com.mongo.personas.models.PersonaResponseGetAll;

public interface PersonaService {

	ResponseEntity<PersonaDto> crearPersona(Persona persona);
	ResponseEntity<Persona> getPersona(String id);
	ResponseEntity<PersonaResponseGetAll> getAllPersonas(String nombre, Integer edad, String sexo, Integer page, Integer size);
	ResponseEntity<Persona> updatePersona(Persona persona);
	ResponseEntity<String> deletePersona(String id);
	
	ResponseEntity<List<PersonaDto>> crearPersonas(List<Persona> listPersona);
	
}

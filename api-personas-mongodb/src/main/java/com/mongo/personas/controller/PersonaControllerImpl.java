package com.mongo.personas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mongo.personas.entity.Persona;
import com.mongo.personas.models.PersonaDto;
import com.mongo.personas.models.PersonaResponseGetAll;
import com.mongo.personas.service.PersonaService;

@RestController
@RequestMapping("persona")
public class PersonaControllerImpl implements PersonaController{

	@Autowired
	private PersonaService personaService;
	
	@Override
	public ResponseEntity<PersonaDto> crearPersona(Persona persona) {
		
		return personaService.crearPersona(persona);
	}

	@Override
	public ResponseEntity<Persona> getPersona(String id) {
		
		return personaService.getPersona(id);
	}

	@Override
	public ResponseEntity<PersonaResponseGetAll> getAllPersona(String nombre, Integer edad, String sexo, Integer page, Integer size) {
		
		return personaService.getAllPersonas(nombre, edad, sexo, page, size);
	}

	@Override
	public ResponseEntity<Persona> updatePersona(Persona persona) {
		
		return personaService.updatePersona(persona);
	}

	@Override
	public ResponseEntity<String> deletePersona(String id) {
		
		return personaService.deletePersona(id);
	}

	@Override
	public ResponseEntity<List<PersonaDto>> crearPersonas(List<Persona> listPersona) {
		
		return personaService.crearPersonas(listPersona);
	}

}

package com.mongo.personas.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.mongo.personas.entity.Persona;
import com.mongo.personas.models.PersonaDto;
import com.mongo.personas.models.PersonaResponseGetAll;

public interface PersonaController {

	@PostMapping
	ResponseEntity<PersonaDto> crearPersona(@RequestBody Persona persona);
	
	@GetMapping("/{id}")
	ResponseEntity<Persona> getPersona(@PathVariable("id") String id);
	
	@GetMapping("/all")
	ResponseEntity<PersonaResponseGetAll> getAllPersona(
			@RequestParam(required = false) String nombre,
			@RequestParam(required = false) Integer edad,
			@RequestParam(required = false) String sexo,
			@RequestParam(required = false, defaultValue = "0") Integer page,
			@RequestParam(required = false, defaultValue = "3") Integer size);
	
	@PutMapping
	ResponseEntity<Persona> updatePersona(@RequestBody Persona persona);
	
	@DeleteMapping("/{id}")
	ResponseEntity<String> deletePersona(@PathVariable String id);
	
	@PostMapping("/all")
	ResponseEntity<List<PersonaDto>> crearPersonas(@RequestBody List<Persona> listPersona);
	
}

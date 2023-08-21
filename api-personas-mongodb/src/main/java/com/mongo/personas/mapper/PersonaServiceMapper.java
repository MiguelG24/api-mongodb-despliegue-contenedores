package com.mongo.personas.mapper;

import org.springframework.stereotype.Component;

import com.mongo.personas.entity.Persona;
import com.mongo.personas.models.PersonaDto;

@Component
public class PersonaServiceMapper {

	public PersonaDto personaToPersonaDto(Persona persona) {
		
		return PersonaDto.builder()
				.id(persona.getId())
				.nombre(persona.getNombre())
				.apellidoPaterno(persona.getApellidoPaterno())
				.edad(persona.getEdad())
				.build();
	}
}

package com.mongo.personas.models;

import java.util.List;

import com.mongo.personas.entity.Persona;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonaResponseGetAll {

	private List<Persona> listPersonas;
	
	private Pagination pagination;
}

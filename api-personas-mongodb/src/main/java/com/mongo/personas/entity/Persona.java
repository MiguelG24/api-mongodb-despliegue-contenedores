package com.mongo.personas.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document("persona")
public class Persona {

	@Id
	private String id;
	
	private String nombre;
	
	private String apellidoPaterno;
	
	private String apellidoMaterno;
	
	private Integer edad;
	
	private String sexo;
}

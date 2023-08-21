package com.mongo.personas.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.mongo.personas.entity.Persona;

public interface PersonaRepository extends MongoRepository<Persona, String>{
	
	Page<Persona> findAll(Pageable pageable);
	
	Page<Persona> findByEdad(Integer edad, Pageable pageable);
	
	Page<Persona> findBySexo(String sexo, Pageable pageable);
	
	Page<Persona> findAllByEdadAndSexo(Integer edad, String sexo, Pageable pageable);
}

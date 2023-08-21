package com.mongo.personas.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.MongoRegexCreator;
import org.springframework.data.mongodb.core.query.MongoRegexCreator.MatchMode;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mongo.personas.entity.Persona;
import com.mongo.personas.exception.PersonaNoEncontradaException;
import com.mongo.personas.mapper.PersonaServiceMapper;
import com.mongo.personas.models.Pagination;
import com.mongo.personas.models.PersonaDto;
import com.mongo.personas.models.PersonaResponseGetAll;
import com.mongo.personas.repository.PersonaRepository;
import com.mongodb.client.result.UpdateResult;

@Service
public class PersonaServiceImpl implements PersonaService{

	@Autowired
	private PersonaRepository personaRepository;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private MongoOperations mongoOperations;
	
	@Autowired
	private PersonaServiceMapper personaServiceMapper;

	@Override
	public ResponseEntity<PersonaDto> crearPersona(Persona persona) {
		
		persona = personaRepository.insert(persona);
		
		PersonaDto personaDto = personaServiceMapper.personaToPersonaDto(persona);
		
		return ResponseEntity.ok(personaDto);
	}

	@Override
	public ResponseEntity<Persona> getPersona(String id) {
		
		Persona persona = personaRepository.findById(id)
				.orElseThrow(
						() -> PersonaNoEncontradaException.from("Persona no encontrada",
								id));
		
		return ResponseEntity.ok(persona);
	}

	@Override
	public ResponseEntity<PersonaResponseGetAll> getAllPersonas(String nombre, Integer edad, String sexo, Integer page, Integer size) {
		
		Pageable pageable = PageRequest.of(page, size);
		
		Query query = this.createQueryDynamic(nombre, edad, sexo);
		
		long count = mongoOperations.count(query, Persona.class);
		
		List<Persona> listPersona = mongoOperations.find(query.with(pageable), Persona.class);

	    Page<Persona> pagePersona = new PageImpl<>(listPersona , pageable, count);
		
//		Page<Persona> pagePersona = PageableExecutionUtils.getPage(listPersona, pageable, 
//				() -> mongoTemplate.count(query, Persona.class));
		
	    PersonaResponseGetAll personaResponseGetAll = this.mapperPersonaResponseGetAll(pagePersona);
				
		return ResponseEntity.ok(personaResponseGetAll);
	}

	@Override
	public ResponseEntity<Persona> updatePersona(Persona persona) {
		
		Query query = new Query(Criteria.where("id").is(persona.getId()));
		Update update = toUpdate(persona);
		
		UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Persona.class);
		
		Optional.ofNullable(updateResult.getUpsertedId())
				.orElseThrow(() -> PersonaNoEncontradaException.from(
						"La persona que intentó actualizar no se enuentra",	persona.getId()));
		
		return ResponseEntity.ok(persona);
	}

	@Override
	public ResponseEntity<String> deletePersona(String id) {
		
		Persona persona = personaRepository.findById(id)
				.orElseThrow(
						() -> PersonaNoEncontradaException.from("Persona no encontrada",
								id));
		
		personaRepository.delete(persona);
		
		return ResponseEntity.ok("Persona eliminada");
	}

	@Override
	public ResponseEntity<List<PersonaDto>> crearPersonas(List<Persona> listPersona) {
		
		Boolean nulos = listPersona.parallelStream().anyMatch(Objects::isNull);
		
		if (nulos) 
			throw new PersonaNoEncontradaException("La lista contiene objetos nulos", null);	
		
		List<PersonaDto> listPersonaDto = 
				personaRepository.saveAll(listPersona)
						.parallelStream()
						.map(persona -> personaServiceMapper.personaToPersonaDto(persona))
						.collect(Collectors.toList());
		
		return ResponseEntity.ok(listPersonaDto);
	}

	private Update toUpdate(Persona persona) {
		Update update = new Update();
		update.set("nombre", persona.getNombre());
		update.set("apellidoPaterno", persona.getApellidoPaterno());
		update.set("apellidoMaterno", persona.getApellidoMaterno());
		update.set("edad", persona.getEdad());
		update.set("sexo", persona.getSexo());
		
		return update;
	}
	
	private PersonaResponseGetAll mapperPersonaResponseGetAll(Page<Persona> pagePersona) {
		
		List<Persona> listPersona = pagePersona.getContent();
		
		Pagination pagination = Pagination.builder()
				.totalItems(pagePersona.getTotalElements())
				.totalPages(pagePersona.getTotalPages())
				.currentPages(pagePersona.getNumber())
				.previosPage(pagePersona.getPageable().previousOrFirst().getPageNumber())
				.nextPage(pagePersona.hasNext() 
						? pagePersona.getPageable().next().getPageNumber()
						: pagePersona.getPageable().getPageNumber())
				.build();
		
		return PersonaResponseGetAll.builder()
				.listPersonas(listPersona)
				.pagination(pagination)
				.build();
	}
	
	private Query createQueryDynamic(String nombre, Integer edad, String sexo) {
		
		final Query query = new Query();
		final List<Criteria> criterias = new ArrayList<>();
		
		if (Objects.nonNull(nombre)) {
			criterias.add(Criteria.where("nombre")
					.regex(MongoRegexCreator.INSTANCE.toRegularExpression(nombre, 
							MatchMode.CONTAINING), "i"));
		}
		if (Objects.nonNull(sexo)) {
			criterias.add(Criteria.where("sexo")
					.regex(MongoRegexCreator.INSTANCE.toRegularExpression(sexo, 
							MatchMode.CONTAINING), "i"));
		}
		
		if (Objects.nonNull(edad)) {
			criterias.add(Criteria.where("edad").is(edad));
		}
		
		if (!criterias.isEmpty()) {
			query.addCriteria(new Criteria().andOperator(criterias.toArray(new Criteria[0])));
		}
		
		return query;
	}
}

package com.mongo.personas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongo.personas.entity.Persona;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;

@Configuration
public class MongoDbConfig {
	
	@Autowired
	private MongoClient mongoClient;
	
	@Value("${spring.data.mongodb.database}")
	private String dbName;

	@Bean
	MongoCollection<Persona> createMongoCollection(){
		
		MongoCollection<Persona> collection = 
				mongoClient.getDatabase(dbName).getCollection("persona", Persona.class);
		
		return collection;
	}
}

package com.ciecas.reactor.app.models.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.ciecas.reactor.app.models.documents.Researcher;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/#mongodb.repositories.queries

@Repository
public interface ResearcherDao extends ReactiveMongoRepository<Researcher, String> {

	// Que tengan ese nombre y apellido
	public Flux<Researcher> findBySurnameAndName(String surname, String name);
	
	
	// Encontrar la primera aparicion con el idGoogle enviado
	public Mono<Researcher> findFirstByIdGoogleScholar(String idGoogleScholar);
	// Encuentra todos sin que se repitan
	public Flux<Researcher> findDistinctResearchersBySurnameOrName(String surname, String name);
	
	
	// Buscamos por varias propiedades ignorando las mayusculas y minusculas
	public Flux<Researcher> findBySurnameAndNameAllIgnoreCase(String surname, String name);
	// Buscamos por 1 propiedad en particular ignorando las mayusculas y minusculas
	public Flux<Researcher> findBySurnameIgnoreCase(String surname);
	
	
	// Buscar por apellidos y ordenar el resultado por nombre de forma ascendente y descendente
	public Flux<Researcher> findBySurnameOrderByNameAsc(String surname);
	public Flux<Researcher> findBySurnameOrderByNameDesc(String surname);
	
	// Buscar por nombre que contenga
	public Flux<Researcher> findByNameContaining(String name);
	
	// public Flux<Researcher> findByActiveIsTrue()
	// findByFechaCreacionAfter(Date date)	findByFechaCreacionBefore(Date date)
	// findByFirstnameNotNull()
	
}

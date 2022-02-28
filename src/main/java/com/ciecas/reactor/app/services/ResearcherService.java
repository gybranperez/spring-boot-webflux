package com.ciecas.reactor.app.services;

import com.ciecas.reactor.app.models.documents.Researcher;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ResearcherService {
	
	public Flux<Researcher> findAll();
	public Mono<Researcher> findById(String id);
	
	public Mono<Researcher> save(Researcher researcher);
	
	public Mono<Void> delete(String id);
}

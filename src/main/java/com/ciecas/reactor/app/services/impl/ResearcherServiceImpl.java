package com.ciecas.reactor.app.services.impl;

import java.util.Comparator;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ciecas.reactor.app.models.dao.ResearcherDao;
import com.ciecas.reactor.app.models.documents.Researcher;
import com.ciecas.reactor.app.services.ResearcherService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ResearcherServiceImpl implements ResearcherService{
	
	@Autowired
	private ResearcherDao dao;

	@Override
	public Flux<Researcher> findAll() {
		return dao.findAll().sort(Comparator.comparing(Researcher::getSurname));
	}

	@Override
	public Mono<Researcher> findById(String id) {
		return dao.findById(id);
		/*
		 Mono<Researcher> researcher = dao .findAll() .filter(r ->
		 r.getId().equals(id)) .next() .doOnNext(r -> log.info(r.toString()));
		 */
	}
	
	@Override
	public Mono<Researcher> insert(Researcher researcher) {
		researcher.setCreationDate(new Date());
		researcher.setLastUpdate(new Date());
		return dao.insert(researcher);
	}

	@Override
	public Mono<Researcher> save(Researcher researcher) {
		researcher.setLastUpdate(new Date());
		return dao.save(researcher);
	}

	@Override
	public Mono<Void> delete(String id) {
		return dao.deleteById(id);
	}
	
	
	
}

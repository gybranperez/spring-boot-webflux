package com.ciecas.reactor.app.controllers;

import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ciecas.reactor.app.models.dao.ResearcherDao;
import com.ciecas.reactor.app.models.documents.Researcher;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/researcher")
public class ResearcherRestController {

	@Autowired
	private ResearcherDao dao;
	
	private static final Logger log = LoggerFactory.getLogger(ResearcherRestController.class);
	
	@GetMapping
	public Flux<Researcher> listAll() {
		
		Flux<Researcher> researchers = dao
										.findAll()
										.sort(Comparator.comparing(Researcher::getName))
										.map(r -> {
											r.setName(r.getName().toUpperCase());
											r.setSurname(r.getSurname().toUpperCase());
											return r;
										})
										.doOnNext(res -> log.info(res.getName()));
		
		return researchers;
	}
	
	@GetMapping("/{id}")
	public Mono<Researcher> getById(@PathVariable(name = "id") String id){
		//Mono<Researcher> researcher = dao.findById(id);
		Mono<Researcher> researcher = dao
				.findAll()
				.filter(r -> r.getId().equals(id))
				.next()
				.doOnNext(r -> log.info(r.toString()));
		return researcher;
	}
	
	
}

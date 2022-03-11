package com.ciecas.reactor.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ciecas.reactor.app.models.documents.Researcher;
import com.ciecas.reactor.app.services.ResearcherService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/researcher")
public class ResearcherRestController {

	@Autowired
	private ResearcherService service;
	
	//private static final Logger log = LoggerFactory.getLogger(ResearcherRestController.class);
	
	@GetMapping
	public Flux<Researcher> listAll() {
		return service.findAll();
	}
	
	@GetMapping("/{id}")
	public Mono<Researcher> getById(@PathVariable(name = "id") String id){
		return service.findById(id);
	}
	
	
	@PostMapping
	public Mono<Researcher> create(@RequestBody Researcher r){
		return service.insert(r);
		
	}
	
	
}

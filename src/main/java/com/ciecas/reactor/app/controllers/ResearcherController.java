package com.ciecas.reactor.app.controllers;

import java.time.Duration;
import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;

import com.ciecas.reactor.app.models.documents.Researcher;
import com.ciecas.reactor.app.services.ResearcherService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SessionAttributes("researcher")
@Controller
public class ResearcherController {

	@Autowired
	private ResearcherService dao;
	
	private static final Logger log = LoggerFactory.getLogger(ResearcherController.class);
	
	@GetMapping({"/listar", "/"})
	public Mono<String> listar(Model model) {
		
		Flux<Researcher> researchers = dao
										.findAll()
										.sort(Comparator.comparing(Researcher::getName))
										.map(r -> {
											r.setName(r.getName().toUpperCase());
											r.setSurname(r.getSurname().toUpperCase());
											return r;
										});
		
		researchers.subscribe(res -> log.info(res.getName()));
		
		model.addAttribute("researchers", researchers);
		model.addAttribute("title", "listado de investigadores");
		
		return Mono.just("listar");
	}
	
	@GetMapping("/form")
	public Mono<String> crear(Model model){
		
		model.addAttribute("researcher", new Researcher());
		model.addAttribute("title", "Nuevo investigador");
		
		return Mono.just("form");
	}
	
	@GetMapping("/form/{id}")
	public Mono<String> editar(@PathVariable String id, Model model){
		
		Mono<Researcher> researcherMono = dao
				.findById(id)
				.doOnNext(r -> log.info("Investigador: ".concat(r.getName())))
				.defaultIfEmpty(new Researcher());
		
		model.addAttribute("researcher", researcherMono);
		model.addAttribute("title", "Nuevo investigador");
		
		return Mono.just("form");
	}
	
	@GetMapping("/form-v2/{id}")
	public Mono<String> editarv2(@PathVariable String id, Model model){
		
		return dao
				.findById(id)
				.doOnNext(r -> {
						log.info("Investigador: ".concat(r.getName()));

						model.addAttribute("researcher", r);
						model.addAttribute("title", "Nuevo investigador");
					})
				.defaultIfEmpty(new Researcher())
				
				.flatMap(r -> {
					return (r.getId() == null) ? Mono.error(new InterruptedException("No existe el investigador")) : Mono.just(r);
				})
				
				.then(Mono.just("form"))
				
				.onErrorResume(ex -> Mono.just("redirect:/listar?error=no+existe+el+producto"));
		
		
	}
	
	@PostMapping("/form")
	public Mono<String> guardar(Researcher r, SessionStatus status){	
		status.setComplete();
		if( r.getId() != null ) {
			return dao.save(r).thenReturn("redirect:/listar");
		} else {
			return dao.insert(r).thenReturn("redirect:/listar");
		}
	}
	
	
	
	@DeleteMapping("/{id}")
	public Mono<Void> delete(@PathVariable String id){
		return dao.findById(id).flatMap(r -> dao.delete(id));
	}
	
	
	/*
	 ---------------------------------   OTROS LISTADOS   --------------------------------- 
	*/
	
	
	@GetMapping("/listarDD")
	public String listarDataDriver(Model model) {
		
		Flux<Researcher> researchers = dao
										.findAll()
										.sort(Comparator.comparing(Researcher::getName))
										.map(r -> {
											r.setName(r.getName().toUpperCase());
											r.setSurname(r.getSurname().toUpperCase());
											return r;
										})
										.delayElements(Duration.ofSeconds(1))
										.repeat(3);
		
		researchers.subscribe(
				//res -> log.info(res.getName())
				);
		
		model.addAttribute("researchers", new ReactiveDataDriverContextVariable(researchers, 1));
		model.addAttribute("title", "listado de investigadores");
		
		return "listar";
	}
	
	@GetMapping("/listarFull") // cuando se piden Pocos datos por ejemplo paginacion
	public String listarFull(Model model) {
		
		Flux<Researcher> researchers = dao
										.findAll()
										.sort(Comparator.comparing(Researcher::getName))
										.map(r -> {
											r.setName(r.getName().toUpperCase());
											r.setSurname(r.getSurname().toUpperCase());
											return r;
										})
										.repeat(500);
		
		researchers.subscribe(
				//res -> log.info(res.getName())
				);
		
		model.addAttribute("researchers", new ReactiveDataDriverContextVariable(researchers, 1));
		model.addAttribute("title", "listado de investigadores");
		
		return "listar";
	}
	
	@GetMapping("/listarChunked") // cuando se piden Pocos datos por ejemplo paginacion
	public String listarChunked(Model model) {
		
		Flux<Researcher> researchers = dao
										.findAll()
										.sort(Comparator.comparing(Researcher::getName))
										.map(r -> {
											r.setName(r.getName().toUpperCase());
											r.setSurname(r.getSurname().toUpperCase());
											return r;
										})
										.repeat(5000);
		
		researchers.subscribe(
				//res -> log.info(res.getName())
				);
		
		model.addAttribute("researchers", new ReactiveDataDriverContextVariable(researchers, 1));
		model.addAttribute("title", "listado de investigadores CHUNKED");
		
		return "listar-chunked";
	}
	
}

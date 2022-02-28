package com.ciecas.reactor.app.controllers;

import java.time.Duration;
import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;

import com.ciecas.reactor.app.models.dao.ResearcherDao;
import com.ciecas.reactor.app.models.documents.Researcher;

import reactor.core.publisher.Flux;


@Controller
public class ResearcherController {

	@Autowired
	private ResearcherDao dao;
	
	private static final Logger log = LoggerFactory.getLogger(ResearcherController.class);
	
	@GetMapping({"/listar", "/"})
	public String listar(Model model) {
		
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
		
		return "listar";
	}
	
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
		
		researchers.subscribe(res -> log.info(res.getName()));
		
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
		
		researchers.subscribe(res -> log.info(res.getName()));
		
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
		
		researchers.subscribe(res -> log.info(res.getName()));
		
		model.addAttribute("researchers", new ReactiveDataDriverContextVariable(researchers, 1));
		model.addAttribute("title", "listado de investigadores CHUNKED");
		
		return "listar-chunked";
	}
	
}

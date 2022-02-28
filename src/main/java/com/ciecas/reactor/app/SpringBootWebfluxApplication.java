package com.ciecas.reactor.app;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.ciecas.reactor.app.models.dao.ResearcherDao;
import com.ciecas.reactor.app.models.documents.Researcher;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootWebfluxApplication implements CommandLineRunner{
	
	@Autowired
	private ResearcherDao dao;
	
	@Autowired
	private ReactiveMongoTemplate mongoTemplate;
	
	private static final Logger log = LoggerFactory.getLogger(SpringBootWebfluxApplication.class);
	
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebfluxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		mongoTemplate.dropCollection("researchers").subscribe();
		
		Flux.just(
				new Researcher("Gybran", "Perez", "gybranperez@gmail.com"),
				new Researcher("Juar", "Perez", "juannperez@gmail.com", "dsfsfdfs"),
				new Researcher("Alfredo", "james", "aljames@gmail.com"),
				new Researcher("jaja", "rez", "jajarez@gmail.com")
				)
			.flatMap(researcher -> {
				researcher.setCreationDate(new Date());
				researcher.setLastUpdate(new Date());				
				return dao.save(researcher);
			})
			.subscribe(researcher -> log.info( String.format("Insert: %s\t%s - %s", researcher.getId(), researcher.getName(), researcher.getCreationDate().toString()) ));
		
		
	}

}

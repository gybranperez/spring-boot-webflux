package com.ciecas.reactor.app.models.documents;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "researchers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Researcher {
	
	@Id
	private String id;
	
	private String name;
	private String surname;
	
	private String email;
	private String idGoogleScholar;
	
	private Date creationDate;
	private Date lastUpdate;
	
	public Researcher(String name, String surname, String email) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
	}

	public Researcher(String name, String surname, String email, String idGoogleScholar) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.idGoogleScholar = idGoogleScholar;
	}
	
	
	
}

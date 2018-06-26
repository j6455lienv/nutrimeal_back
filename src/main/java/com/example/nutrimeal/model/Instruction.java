package com.example.nutrimeal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Instruction {

	@Getter
	@Setter
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_INSTRUCTION")
    private Long idInstruction;
	
	@Getter
	@Setter
	@Column(name = "CONTENU")
	private String contenu;
	
	@Getter
	@Setter
	@Column(name = "ID_RECETTE")
	private Long idRecette;
}

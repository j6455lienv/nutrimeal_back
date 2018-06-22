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
    private Long idInstruction;
	
	@Getter
	@Setter
	@Column
	private Long chrono;
	
	@Getter
	@Setter
	@Column
	private String libelle;
	
	@Getter
	@Setter
	@Column
	private Long idRecette;
}

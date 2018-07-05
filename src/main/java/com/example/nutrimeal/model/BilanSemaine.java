package com.example.nutrimeal.model;

import java.util.Set;

import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BilanSemaine {

	private Set<Recette> listeRecettes;

	@Transient
	private Double bilanfer;
	
	@Transient
	private Double bilanSodium;
	
	@Transient
	private Double bilanVitamineD;
	
	@Transient
	private Double bilanVitamineC;
	
	@Transient
	private Double bilanVitamineB12;
	
}

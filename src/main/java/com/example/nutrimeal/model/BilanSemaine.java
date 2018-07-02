package com.example.nutrimeal.model;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

public class BilanSemaine {

	@Getter
	@Setter
	private Set<Recette> listeRecettes;
	
	@Getter
	@Setter
	private Double bilanMineral;
	
	@Getter
	@Setter
	private Double bilanVitaminal;
	
}

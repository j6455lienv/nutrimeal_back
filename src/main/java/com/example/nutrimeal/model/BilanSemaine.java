package com.example.nutrimeal.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class BilanSemaine {

	@Getter
	@Setter
	private List<Recette> listeRecettes;
	
	@Getter
	@Setter
	private Double bilanMineral;
	
	@Getter
	@Setter
	private Double bilanVitaminal;
	
}

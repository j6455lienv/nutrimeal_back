package com.example.nutrimeal.model;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BilanSemaine {

	private Set<Recette> listeRecettes;

	private Double bilanMineral;
	

	private Double bilanVitaminal;
	
}

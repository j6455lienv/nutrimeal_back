package com.example.nutrimeal.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Gaetan
 * 
 * Classe pratique utilisée lors de la création de paires clés / valeurs
 * 
 * Méthode associée dans MethodesPratiquesRepository
 *
 */
public class Paire {

	@Getter
	@Setter
	private Long key;
	
	@Getter
	@Setter
	private String value;
	
}

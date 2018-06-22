package com.example.nutrimeal.repository;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author Gaetan Inidjel
 *
 */
@Repository
public class MethodesPratiquesRepository {

	
	/**
	 * Cette méthode prend en entrée un nombre, et le renvoie avec deux chiffres significatifs
	 * 
	 * @param nombre
	 * 			nombre de type Double en entrée
	 * @return nombre de type Double arrondi à deux chiffres significatifs
	 */
	public Double deuxChiffresSignificatifs(Double nombre) {
		
		return Math.round(nombre*100d)/100d;	
		
	}
	
}

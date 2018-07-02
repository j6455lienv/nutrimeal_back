package com.example.nutrimeal.repository;

import org.springframework.stereotype.Repository;

import com.example.nutrimeal.model.Paire;

/**
 * 
 * @author Gaetan Inidjel
 *
 */
@Repository
public class MethodesPratiquesRepository {

	
	/**
	 * Cette méthode prend en entrée un nombre décimal, et le renvoie avec deux chiffres significatifs
	 * 
	 * @param nombre
	 * 			nombre de type Double en entrée
	 * @return nombre de type Double arrondi à deux chiffres significatifs
	 */
	public Double deuxChiffresSignificatifs(Double nombre) {
		
		return Math.round(nombre*100d)/100d;	
		
	}
	
	/**
	 * Cette méthode sert à créer une List<Paire> composée de couples ID / Valeurs
	 * 
	 * @param key
	 * 		Identifiant de la valeur
	 * @param value
	 * 		Valeur associée à la clé
	 * @return
	 */
	public Paire creationPaireKeyValue(Long key, String value){
		
		Paire paire = new Paire();
		paire.setKey(key);
		paire.setValue(value);
			
		return paire;
	}
	
}

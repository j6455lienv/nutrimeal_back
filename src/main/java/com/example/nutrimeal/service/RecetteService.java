package com.example.nutrimeal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.nutrimeal.model.Recette;
import com.example.nutrimeal.repository.RecetteRepository;

/**
 * 
 * @author Gaetan Inidjel
 *
 */
@Service
public class RecetteService {

	@Autowired 
	private RecetteRepository recetteRepository;
	
	/**
	 * Méthode qui retourne une recette lorsque l'on donne une idRecette
	 * 
	 * @param id
	 * 			id de la recette à récupérer
	 * @return
	 * 			Un objet Recette
	 */
	public Recette getRecetteById (Long id){
		return recetteRepository.getOne(id);
	}
	
}

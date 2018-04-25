package com.example.nutrimeal.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.nutrimeal.model.Recette;
import com.example.nutrimeal.repository.RecetteRepository;

@Service
public class RecetteService {
	
	@Autowired
	private RecetteRepository recetteRepository;

	/** Récupère une recette persistée à partir de son id.
	 * @param id l'id de la recette
	 * @return la recette persistée */
	public Optional<Recette> get(Long id) {
		return recetteRepository.findById(id);
	}

}

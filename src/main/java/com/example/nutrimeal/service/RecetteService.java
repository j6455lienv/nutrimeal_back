package com.example.nutrimeal.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	/** Récupère une page de résultat de recette contenant la chaine recherchée.
	 * @param string la chaine recherchée
	 * @param pageable les paramètres de pagination
	 * @return la liste des recettes correspondant à la recherche*/
	public Page<Recette> findRecetteContaining(String string, Pageable pageable) {
		return recetteRepository.findByNomContainsIgnoreCaseOrderByNom(pageable, string);
	}

	public Set<Recette> findRandomRecettesWithImages() {
		List<Recette> recetteListe = recetteRepository.findByBase64ImageCodeNotNull();7
		int size = recetteListe.size();
		int randomIndex;
		int i = 0;
		
		while (i < 5) {
			randomIndex = (int) Math.round(Math.random() * size);
			recetteListe.get
			i++;
		}

		return null;
	}
}

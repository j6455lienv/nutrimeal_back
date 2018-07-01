package com.example.nutrimeal.service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.nutrimeal.model.Recette;
import com.example.nutrimeal.repository.RecetteRepository;

@Service
public class RecetteService {
	
	@Autowired
	private RecetteRepository recetteRepository;
	
	/** Nombre de recettes aléatoirement tirées de la base pour le carroussel.*/
	private static final int CARROUSSEL_RECETTE_NUMBER = 10;

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

	/**  Récupère aléatoirement des recettes avec images en base pour le carroussel de la page d'accueil.
	 * @return la liste des recettes pris aléatoirement en base*/
	public Set<Recette> findRandomRecettesWithImages() {
		int elementsPerPage = 20;
		Set<Recette> result = new HashSet<>();

		Page<Recette> recetteListe = recetteRepository.findByBase64ImageCodeNotNull(PageRequest.of(0, elementsPerPage));
		
		if (recetteListe.hasContent()) {
			long totalElementsNumber = recetteListe.getTotalElements();
			int i = 0;
			int randomRecetteIndex;
			int askedPage;
			int elementIndexInPage;
			List<Recette> pageContent;
			
			while (i < CARROUSSEL_RECETTE_NUMBER) {
				randomRecetteIndex = (int) (Math.floor(Math.random() * totalElementsNumber));
				askedPage = (int) (Math.floor(randomRecetteIndex / elementsPerPage));
				
				recetteListe = recetteRepository.findByBase64ImageCodeNotNull(PageRequest.of(askedPage, elementsPerPage));
				
				pageContent = recetteListe.getContent();
				elementIndexInPage = randomRecetteIndex % elementsPerPage;
				
				result.add(pageContent.get(elementIndexInPage));
				i++;
			}
		}

		return result;
	}
}

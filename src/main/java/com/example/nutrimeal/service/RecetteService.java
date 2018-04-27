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
	
	private static final int CARROUSSEL_ELEMENT_PAR_PAGE = 10;

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
		Set<Recette> result = new HashSet<>();
		
		Pageable pageable = new PageRequest(0,CARROUSSEL_ELEMENT_PAR_PAGE);
		Page<Recette> recetteListe = recetteRepository.findByBase64ImageCodeNotNull(pageable);
		int totalNumberOfElements = (int) recetteListe.getTotalElements();
		
		if (totalNumberOfElements == 0) {
			return new HashSet<Recette>();
		}
		
		int randomIndex;
		int askedPage;
		int elementIndexInPage;
		List<Recette> pageContent;
		
		while (result.size() < 10) {
			randomIndex = (int) Math.round(Math.random() * (totalNumberOfElements-1));
			askedPage = (int) Math.floor(randomIndex / CARROUSSEL_ELEMENT_PAR_PAGE);
			recetteListe = recetteRepository.findByBase64ImageCodeNotNull(new PageRequest(askedPage, CARROUSSEL_ELEMENT_PAR_PAGE));
			
			pageContent = recetteListe.getContent();
			elementIndexInPage = randomIndex % CARROUSSEL_ELEMENT_PAR_PAGE;
			
			result.add(pageContent.get(elementIndexInPage));
		}
		return result;
	}
}

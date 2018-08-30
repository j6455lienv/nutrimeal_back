package com.example.nutrimeal.service;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.nutrimeal.model.Recette;
import com.example.nutrimeal.model.RecetteIngredient;
import com.example.nutrimeal.repository.RecetteRepository;

import utils.Constantes;
import utils.MethodesPratiques;


/**
 * 
 * @author Gaetan Inidjel
 *
 */
@Service
public class RecetteService {
	
	@Autowired 
	RecetteRepository recetteRepository;
		
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
	
	/**
	 * Méthode qui extrait la liste des recettes au format clé / valeur
	 * 
	 * @return
	 * 		Liste<Paire> utilisée pour alimenter les listes de recettes
	 * @throws SQLException
	 */
    public Map<Long, String> alimentationListesRecettes() throws SQLException{
	
    	List<Recette> listeRecettes = recetteRepository.findAll();
    	
	Map<Long, String> listePaireIdRecetteNomRecette = new HashMap<>();
	
		for (Recette recette : listeRecettes ) 
		{
			String value = recette.getNomRecette();	
			Long key = recette.getId();		
			listePaireIdRecetteNomRecette.put(key, value);			
		}
		
	return listePaireIdRecetteNomRecette;
	
    }

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
		return recetteRepository.findByNomRecetteContainsIgnoreCaseOrderByNomRecette(pageable, string);
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
			
			while (i < Constantes.CARROUSSEL_RECETTE_NUMBER) {
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

	/**
	 * Permet de calculer les apports en vitamines et en minéraux d'une recette.
	 * @param recette la recette dont on veut calculer les apports
	 * @return la recette mise à jour avec ses apports
	 * @throws Exception 
	 */
	public Recette computeValues(Recette recette) throws Exception {
		
		List<Double> apports = calculNutrimentsParRecette_So_Fe_VitC_VitD_VitB12(recette);
		
		recette.setSodiumParPortion(MethodesPratiques.deuxChiffresSignificatifs(
				apports.get(0) * 100d / Constantes.Sodium));
		recette.setFerParPortion(MethodesPratiques.deuxChiffresSignificatifs(
				apports.get(1) * 100d / Constantes.Fer));
		recette.setVitamineCParPortion(MethodesPratiques.deuxChiffresSignificatifs(
				apports.get(2) * 100d / Constantes.VitamineC));
		recette.setVitamineDParPortion(MethodesPratiques.deuxChiffresSignificatifs(
				apports.get(3) * 100d / Constantes.VitamineD));
		recette.setVitamineB12ParPortion(MethodesPratiques.deuxChiffresSignificatifs(
				apports.get(4) * 100d / Constantes.VitamineB12));
		
		return recette;
	}
	
	/**
	 * La méthode renvoie une liste de valeurs nutritionnelles
	 * 
	 * @param Recette
	 * 		Un objet Recette
	 * @return vitaminesTotales
	 * 		Retourne le total en nutriments de la recette (List<Double> dans l'ordre So, Fe, VC,, VD, VB12)
	 * @throws Exception 
	 */
	public List<Double> calculNutrimentsParRecette_So_Fe_VitC_VitD_VitB12(Recette recette) throws Exception {
		
		Double sodium = 0d;
		Double fer = 0d;
		Double vitamineC = 0d;
		Double vitamineD = 0d;
		Double vitamineB12 = 0d;
		// Les ingrédients sont récupérés
			Set<RecetteIngredient> ingredients = recette.getRecetteIngredients();
			List<Double> apports = new ArrayList<>();
			// Pour chaque ingrédient, la quantité est multipliée par les vitamines par ingrédient.
			for(RecetteIngredient ingredient : ingredients) {
				sodium += ingredient.getQuantite()*ingredient.getIngredients().getSodium();
				fer += ingredient.getQuantite()*ingredient.getIngredients().getFer();
				vitamineC += ingredient.getQuantite()*ingredient.getIngredients().getVitamineC();
				vitamineD += ingredient.getQuantite()*ingredient.getIngredients().getVitamineD();
				vitamineB12 += ingredient.getQuantite()*ingredient.getIngredients().getVitamineB12();
			}
			apports.add(sodium);
			apports.add(fer);
			apports.add(vitamineC);
			apports.add(vitamineD);
			apports.add(vitamineB12);
		return apports;
	}
}

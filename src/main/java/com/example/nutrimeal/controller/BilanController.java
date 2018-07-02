package com.example.nutrimeal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.nutrimeal.model.BilanSemaine;
import com.example.nutrimeal.model.Paire;
import com.example.nutrimeal.model.Recette;
import com.example.nutrimeal.service.BilanService;
import com.example.nutrimeal.service.RecetteService;

/**
 * 
 * @author Gaetan Inidjel
 *
 */
@RestController
public class BilanController {

	@Autowired
	RecetteService recetteService;
	@Autowired
	BilanService bilanService;
	
	/** Méthode du RestController qui calcule le bilan pour les recettes sélectionnées par l'utilisateur
	 *
	 * @param listeRecettes
	 * 			Liste des recettes au format JSON
	 * @return
	 * 			Retourne le bilan : Recettes au format sans mapping et bilan minéral / vitaminal
	 * @throws Exception
	 */
	@RequestMapping(value = "/bilan", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> getListeRecettes(@RequestBody List<Recette> listeRecettes) throws Exception{	
		
		BilanSemaine bilan = bilanService.bilanSemaine(listeRecettes);
		
		if (bilan != null) {
			return new ResponseEntity<>(bilan, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(bilan, HttpStatus.BAD_REQUEST);
		}
	}
	
	/** Méthode du RestController qui envoie un tableau de nom recettes et d'ID_RECETTE au front.
	 *
	 * @param 
	 * 		néant
	 * @return
	 * 		Retourne une liste de Paires clés / valeurs NOM_RECETTE / ID_RECETTE pour les listes de recherche
	 * @throws Exception
	 */
	@RequestMapping(value = "/bilan", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getNomRecettesEtIdRecette() throws Exception{	
		
		List<Paire> listePaires = recetteService.alimentationListesRecettes();
		 
		if (listePaires != null) {
			return new ResponseEntity<>(listePaires, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(listePaires, HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
	
	
}

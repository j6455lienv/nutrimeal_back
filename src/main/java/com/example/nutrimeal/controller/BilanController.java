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
import com.example.nutrimeal.model.Recette;
import com.example.nutrimeal.repository.MethodesPratiquesRepository;
import com.example.nutrimeal.service.BilanService;

/**
 * 
 * @author Gaetan Inidjel
 *
 */
@RestController
public class BilanController {

	@Autowired
	MethodesPratiquesRepository methodes;
	
	@Autowired
	BilanService bilanService;
	
	/**
	 * Méthode du restController qui export en PDF les recettes sélectionnées dans l'onglet Bilan
	 * 
	 * @param listeIdAsString
	 * 			Liste d'ID sous forme de chaine de caractères (ex : "12,13,42")
	 * @return
	 * 			Export pdf du bilan de la semaine
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
	
}

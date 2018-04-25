package com.example.nutrimeal.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.nutrimeal.model.Recette;
import com.example.nutrimeal.service.RecetteService;

@RestController
@RequestMapping(value = "/recette")
public class RecetteRestController {
	
	/** Service de gestion des recettes. */
	@Autowired
	private RecetteService recetteService;
	
	/** Renvoi la recette persistée demandée par une requête.
	 * @param id l'id de la recette
	 * @return une réponse HTTP avec la recette serialisée*/
	@RequestMapping(value = "/{id}")
	public ResponseEntity<Recette> FindThisRecette(@PathVariable("id") Long id ) {
		Optional<Recette> recette = recetteService.get(id);
		if (recette.isPresent()) {
			return new ResponseEntity<>(recette.get(), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}

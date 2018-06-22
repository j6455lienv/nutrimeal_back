package com.example.nutrimeal.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.nutrimeal.model.BilanSemaine;
import com.example.nutrimeal.model.RecetteIngredient;
import com.example.nutrimeal.model.Recette;
import com.example.nutrimeal.repository.MethodesPratiquesRepository;

/**
 * 
 * @author Gaetan Inidjel
 *
 */
@Service
public class BilanService {

	@Autowired
	RecetteService recetteService;
	
	@Autowired
	MethodesPratiquesRepository methodes;
	
	/**
	 * La méthode qui somme les vitamines des ingrédients de la recette
	 * 
	 * @param Recette
	 * 		Un objet Recette
	 * @return vitaminesTotales
	 * 		Retourne le bilan en minéraux de la recette (Double)
	 * @throws Exception 
	 */
	public Double calculVitaminesPourRecette(Recette recette) throws Exception {
	
	
	Double vitaminesTotales = 0d;
	
	//Pour chaque idRecette
		
		// Les ingrédients sont récupérés
		Set<RecetteIngredient> ingredients = recette.getRecetteIngredients();
		
		// Pour chaque ingrédient, la quantité est multipliée par les vitamines par ingrédient.
		for(RecetteIngredient ingredient : ingredients) {
			
			vitaminesTotales += ingredient.getQuantite()*ingredient.getIngredients().getVitamines();
			
			
		}
		return vitaminesTotales;
	}
/**
 * Méthode qui somme les minéraux des ingrédients de la recette
 * 
 * @param Recette
 * 			Un objet Recette
 * @return minerauxTotaux
 * 			Retourne le bilan en minéraux de la recette (Double)
 * @throws Exception 
 */
	public Double calculMinerauxPourRecette(Recette recette) throws Exception {
		
		
		Double minerauxTotaux = 0d;
		
		//Pour chaque idRecette
	
			// Les ingrédients sont récupérés
			Set<RecetteIngredient> ingredients = recette.getRecetteIngredients();
			
			// Pour chaque ingrédient, la quantité est multipliée par les minéraux par ingrédient.
			for(RecetteIngredient ingredient : ingredients) {
			
				minerauxTotaux += ingredient.getQuantite()*ingredient.getIngredients().getMineraux();
			
		}
		return minerauxTotaux;
	}
	
	
/**
 * 	Méthode qui renvoie un JSON pour le calcul du bilan de la semaine
 * 
 * @param listeRecettes
 * 		Liste de Recettes
 * @return
 * 		Un objet BilanSemaine qui sera renvoyé en JSON
 * @throws Exception
 */
public BilanSemaine bilanSemaine(List<Recette> listeRecettes) throws Exception{
				
		Double bilanVitaminesTotales = 0d;
		Double bilanMinerauxTotaux = 0d;
		
		BilanSemaine bilan = new BilanSemaine();
		
		for(Recette recette : listeRecettes) {
			
			recette = recetteService.getRecetteById(recette.idRecette);
			Double bilanVitaminiqueParRecette = calculVitaminesPourRecette(recette);
			Double bilanMineralParRecette = calculMinerauxPourRecette(recette);
			
			bilanVitaminesTotales += bilanVitaminiqueParRecette;
			bilanMinerauxTotaux += bilanMineralParRecette;
			
			recette.setMinerauxParPortion(bilanMineralParRecette);
			recette.setVitaminesParPortion(bilanVitaminiqueParRecette);
		}
		bilan.setListeRecettes(listeRecettes);
		bilan.setBilanMineral(bilanMinerauxTotaux);
		bilan.setBilanVitaminal(bilanVitaminesTotales);
			
		return bilan;
	}
}

package com.example.nutrimeal.service;

import java.util.HashSet;
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
 * 	Méthode qui renvoie un JSON pour le calcul du bilan de la semaine
 * 
 * @param listeRecettes
 * 		Liste de Recettes
 * @return
 * 		Un objet BilanSemaine qui sera renvoyé en JSON
 * @throws Exception
 */
public BilanSemaine bilanSemaine(List<Recette> listeRecettes) throws Exception{
				
		Double bilanVitamineCTotales = 0d;
		Double bilanVitamineDTotales = 0d;
		Double bilanVitamineB12Totales = 0d;
		Double bilanFerTotal = 0d;
		Double bilanSodiumTotal = 0d;
				
		// Création d'un Set sans mapping
		Set<Recette> setRecetteSansMapping = new HashSet<Recette>();
		
		for(Recette recette : listeRecettes) {
			
			// Reconstruction du mapping et calcul des minéraux / Vitamines
			recette = recetteService.getRecetteById(recette.getIdRecette());
			
			Double bilanVitamineCParRecette = 0d;
			Double bilanVitamineDParRecette =  0d;
			Double bilanVitamineB12ParRecette =  0d;
			Double bilanFerParRecette =  0d;
			Double bilanSodiumParRecette =  0d;
			
			
			Set<RecetteIngredient> ingredients = recette.getRecetteIngredients();
			
			// Pour chaque ingrédient, la quantité est multipliée par les minéraux par ingrédient.
			for(RecetteIngredient ingredient : ingredients) {
				bilanFerParRecette += ingredient.getQuantite()*ingredient.getIngredients().getFer();
				bilanSodiumParRecette += ingredient.getQuantite()*ingredient.getIngredients().getSodium();
				bilanVitamineCParRecette += ingredient.getQuantite()*ingredient.getIngredients().getVitamineC();
				bilanVitamineDParRecette += ingredient.getQuantite()*ingredient.getIngredients().getVitamineD();
				bilanVitamineB12ParRecette += ingredient.getQuantite()*ingredient.getIngredients().getVitamineB12();
			}
			
			// Créer un petit objet non mappé (plus léger pour le front)
			Recette RecetteSansMapping = new Recette();
			RecetteSansMapping.setNomRecette(recette.getNomRecette());
			RecetteSansMapping.setFerParPortion(bilanFerParRecette);
			RecetteSansMapping.setSodiumParPortion(bilanSodiumParRecette);
			RecetteSansMapping.setVitamineCParPortion(bilanVitamineCParRecette);
			RecetteSansMapping.setVitamineDParPortion(bilanVitamineDParRecette);
			RecetteSansMapping.setVitamineB12ParPortion(bilanVitamineB12ParRecette);
			
			setRecetteSansMapping.add(RecetteSansMapping);
			
			// Calculer le total pour le bilan
			bilanVitamineCTotales += bilanVitamineCParRecette;
			bilanVitamineDTotales += bilanVitamineDParRecette;
			bilanVitamineB12Totales += bilanVitamineB12ParRecette;
			bilanFerTotal += bilanFerParRecette;
			bilanSodiumTotal += bilanSodiumParRecette;
	
		}
		// Création du bilan de la semaine
		BilanSemaine bilan = new BilanSemaine();
		bilan.setListeRecettes(setRecetteSansMapping);
		bilan.setBilanfer(bilanFerTotal);
		bilan.setBilanSodium(bilanSodiumTotal);
		bilan.setBilanVitamineC(bilanVitamineCTotales);
		bilan.setBilanVitamineD(bilanVitamineDTotales);
		bilan.setBilanVitamineB12(bilanVitamineB12Totales);
		
		return bilan;
	}
}

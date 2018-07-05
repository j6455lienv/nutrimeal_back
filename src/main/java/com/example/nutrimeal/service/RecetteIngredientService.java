package com.example.nutrimeal.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.nutrimeal.model.RecetteIngredient;

@Service
public class RecetteIngredientService {

	private final String UNITE = "unité";

	/** Methode qui return une liste de Double dans l'ordre : Sodium, Fer, Vitamine C, Vitamine D, Vitamine B12
	 * 
	 * @param 
	 * 		recetteIngredient
	 * @return
	 * 		List<Double> de nutriments
	 */
	public List<Double> nutrimentsParIngredients_So_Fe_VC_VD_VB12(RecetteIngredient recetteIngredient){
		
		List<Double> listeNutriments = new ArrayList<Double>(5);
		
		listeNutriments.add(recetteIngredient.getIngredients().getSodium());
		listeNutriments.add(recetteIngredient.getIngredients().getFer());
		listeNutriments.add(recetteIngredient.getIngredients().getVitamineC());
		listeNutriments.add(recetteIngredient.getIngredients().getVitamineD());
		listeNutriments.add(recetteIngredient.getIngredients().getVitamineB12());
		
		return listeNutriments;
	}
	
	/**		Methode qui return la quantité d'ingredients
	 * 
	 * @param recetteIngredient
	 * 		RecetteIngredient qui contient la liste des ingredients et leur quantités
	 * @return
	 * 		String qui affiche la quantité et l'unité.
	 */
	public String quantiteIngredients(RecetteIngredient recetteIngredient) {
		
		String quantiteEtUnite;
		
		if(UNITE.equals(recetteIngredient.getIngredients().getUniteMesure().getLabel())) {
			quantiteEtUnite = recetteIngredient.getQuantite().toString();
		}else {
			quantiteEtUnite = recetteIngredient.getQuantite().toString()
					+ " " + recetteIngredient.getIngredients().getUniteMesure().getLabel();
		}
		return quantiteEtUnite;
	}
}

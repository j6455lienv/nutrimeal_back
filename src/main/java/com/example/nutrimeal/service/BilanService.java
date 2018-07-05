package com.example.nutrimeal.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.nutrimeal.model.BilanSemaine;
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
 * 		Méthode qui renvoie un JSON pour le calcul du bilan de la semaine
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
			
			// Pour chaque ingrédient, la quantité est multipliée par les minéraux par ingrédient.
			List<Double> apports = recetteService.calculNutrimentsParRecette_So_Fe_VitC_VitD_VitB12(recette);
			
			// Créer un petit objet non mappé (plus léger pour le front)
			Recette RecetteSansMapping = new Recette();
			RecetteSansMapping.setNomRecette(recette.getNomRecette());
			RecetteSansMapping.setSodiumParPortion(apports.get(0));
			RecetteSansMapping.setFerParPortion(apports.get(1));
			RecetteSansMapping.setVitamineCParPortion(apports.get(2));
			RecetteSansMapping.setVitamineDParPortion(apports.get(3));
			RecetteSansMapping.setVitamineB12ParPortion(apports.get(4));
			
			setRecetteSansMapping.add(RecetteSansMapping);
			
			// Calculer le total pour le bilan
			bilanFerTotal += apports.get(0);
			bilanSodiumTotal += apports.get(1);
			bilanVitamineCTotales += apports.get(2);
			bilanVitamineDTotales += apports.get(3);
			bilanVitamineB12Totales += apports.get(4);	
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

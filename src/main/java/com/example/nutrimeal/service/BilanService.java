package com.example.nutrimeal.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.nutrimeal.model.BilanSemaine;
import com.example.nutrimeal.model.Recette;
import com.example.nutrimeal.model.enumeration.UniteMesure;

import utils.Constantes;
import utils.MethodesPratiques;

/**
 * 
 * @author Gaetan Inidjel
 *
 */
@Service
public class BilanService {

	@Autowired
	RecetteService recetteService;
	
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
				
		// Création d'un Set Recette sans mapping
		Set<Recette> setRecetteSansMapping = new HashSet<Recette>();
		
		for(Recette recette : listeRecettes) {
			
			// Reconstruction du mapping et calcul des minéraux / Vitamines
			recette = recetteService.getRecetteById(recette.getId());
			
			// Pour chaque ingrédient, la quantité est multipliée par les minéraux par ingrédient.
			List<Double> apports = recetteService.calculNutrimentsParRecette_So_Fe_VitC_VitD_VitB12(recette);
			
			// Créer un petit objet non mappé (plus léger pour le front)
			Recette RecetteSansMapping = new Recette();
			RecetteSansMapping.setNomRecette(recette.getNomRecette());
			RecetteSansMapping.setSodiumParPortion(MethodesPratiques.deuxChiffresSignificatifs(apports.get(0)
					* 100d / Constantes.Sodium ));
			RecetteSansMapping.setFerParPortion(MethodesPratiques.deuxChiffresSignificatifs(apports.get(1)
					* 100d / Constantes.Fer ));
			RecetteSansMapping.setVitamineCParPortion(MethodesPratiques.deuxChiffresSignificatifs(apports.get(2)
					* 100d / Constantes.VitamineC ));
			RecetteSansMapping.setVitamineDParPortion(MethodesPratiques.deuxChiffresSignificatifs(apports.get(3)
					* 100d / Constantes.VitamineD ));
			RecetteSansMapping.setVitamineB12ParPortion(MethodesPratiques.deuxChiffresSignificatifs(apports.get(4)
					* 100d / Constantes.VitamineB12 ));
			
			setRecetteSansMapping.add(RecetteSansMapping);
			
			// Calculer le total pour le bilan
			bilanSodiumTotal += apports.get(0);
			bilanFerTotal += apports.get(1);
			bilanVitamineCTotales += apports.get(2);
			bilanVitamineDTotales += apports.get(3);
			bilanVitamineB12Totales += apports.get(4);	
		}
		// Création du bilan de la semaine
		BilanSemaine bilan = new BilanSemaine();
		
		bilan.setListeRecettes(setRecetteSansMapping);
		bilan.setBilanSodium(bilanSodiumTotal);
		bilan.setBilanfer(bilanFerTotal);
		bilan.setBilanVitamineC(bilanVitamineCTotales);
		bilan.setBilanVitamineD(bilanVitamineDTotales);
		bilan.setBilanVitamineB12(bilanVitamineB12Totales);
		bilan.setUniteMineraux("µg");
				
		return bilan;
	}
}

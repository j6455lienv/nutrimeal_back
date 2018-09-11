package com.example.nutrimeal.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.example.nutrimeal.model.Recette;

/** Classe servant au batch à comprendre comment traiter les informations récupérées. */
public class RecetteProcessor implements ItemProcessor<Recette, Recette> {

	@Override
	public Recette process(Recette recette) throws Exception {
		Recette recetteToPersist = new Recette();
		return recetteToPersist;
	}

}

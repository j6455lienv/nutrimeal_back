package com.example.nutrimeal.service;


import java.io.OutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.nutrimeal.model.Recette;
import com.example.nutrimeal.model.RecetteIngredient;
import com.example.nutrimeal.repository.MethodesPratiquesRepository;
import com.example.nutrimeal.repository.RecetteRepository;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class ExportService {

	private final String UNITE = "unite";
	
	@Autowired 
	RecetteRepository recetteRepository;
	@Autowired
	MethodesPratiquesRepository methodesPratiquesRepository;
	@Autowired
	BilanService bilanService;
	@Autowired
	RecetteService recetteService;
	
	/**
	 * Export PDF du bilan de la semaine
	 * 
	 * @param outputStream
	 * 			Export de Bytes utilisé par PdfWriter pour l'export pdf
	 * @param listeIdAsString
	 * 			Liste d'id en chaine de caractères (ex : "1,2") séparés par des virgules
	 * @throws Exception
	 * 			On throws une exception
	 */
	public void exportBilanPdfService(OutputStream outputStream, List<Recette> listeRecettes) 
			throws Exception  {
		
		Document document = new Document();
		PdfWriter.getInstance(document, outputStream);
				
		document.open();

		Double totalVitamines = 0d;
		Double totalMineraux = 0d;
		
		
		for (Recette recette : listeRecettes) {
			recette = recetteService.getRecetteById(recette.getIdRecette());
			totalVitamines += bilanService.calculVitaminesPourRecette(recette);
			totalMineraux += bilanService.calculMinerauxPourRecette(recette);
			
			PdfPCell cell;
			PdfPTable table = new PdfPTable(4);
			
			
			cell = new PdfPCell(new Phrase("Composition de la recette : " + recette.getNomRecette()));
		    cell.setColspan(4);
			cell.setHorizontalAlignment(1);
			cell.setBackgroundColor(BaseColor.GREEN);
		    table.addCell(cell);
		    
		    String[] entetes = {"Ingredients", "Quantite", "Vitamines", "Mineraux"};
		    
		    for(String entete : entetes) {
		    	cell = new PdfPCell(new Phrase(entete));
		    	cell.setBackgroundColor(BaseColor.CYAN);
			    table.addCell(cell);
		    }
		    
		    Double totalVitaminesRecette = 0d;
		    Double totalMinerauxRecette = 0d;
		    
			for(RecetteIngredient ingredient : recette.getRecetteIngredients()) {
			
				Double vitaminesParIngredient = ingredient.getIngredients().getVitamines()*ingredient.getQuantite()/1000;;
				Double minerauxParIngredient = ingredient.getIngredients().getMineraux()*ingredient.getQuantite()/1000;;
				
				totalVitaminesRecette += vitaminesParIngredient;
				totalMinerauxRecette += minerauxParIngredient;
				
				table.addCell(ingredient.getIngredients().getLibelle());
				
				if(UNITE.equals(ingredient.getIngredients().getUniteMesure())) {
					table.addCell(ingredient.getQuantite().toString());
				}else {
					table.addCell(ingredient.getQuantite().toString()
							+ " " + ingredient.getIngredients().getUniteMesure());
				}
				
				table.addCell(vitaminesParIngredient.toString() + " mg ");
				table.addCell(minerauxParIngredient.toString() + " mg " );
			}
			
			totalVitaminesRecette = methodesPratiquesRepository.
					deuxChiffresSignificatifs(totalVitaminesRecette);
			totalMinerauxRecette = methodesPratiquesRepository.
					deuxChiffresSignificatifs(totalMinerauxRecette);

			table.addCell("Total de la recette");
			table.addCell("");
			table.addCell(totalVitaminesRecette.toString() + " g ");
			table.addCell(totalMinerauxRecette.toString() + " g ");
			table.setSpacingAfter(50);
			
			document.add(table);
		}
				
		document.add(new Paragraph("Le bilan en vitamines est de " + methodesPratiquesRepository.
				deuxChiffresSignificatifs(totalVitamines/1000) + " g " ));	
		document.add(new Paragraph("Le bilan en mineraux est de " + methodesPratiquesRepository.
				deuxChiffresSignificatifs(totalMineraux/1000) + " g "));
		document.close();
	}
	
	
	
	
}

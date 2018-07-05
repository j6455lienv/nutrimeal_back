package com.example.nutrimeal.service;


import java.io.OutputStream;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.nutrimeal.model.Instruction;
import com.example.nutrimeal.model.Recette;
import com.example.nutrimeal.model.RecetteIngredient;
import com.example.nutrimeal.repository.MethodesPratiquesRepository;
import com.example.nutrimeal.repository.RecetteRepository;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class ExportService {

	private final String UNITE = "unité";
	
	@Autowired 
	RecetteRepository recetteRepository;
	@Autowired
	MethodesPratiquesRepository methodesPratiquesRepository;
	@Autowired
	BilanService bilanService;
	@Autowired
	RecetteService recetteService;
	
	
	/**
	 * Export PDF de la recette sélectionnée
	 * 
	 * @param outputStream
	 * 		Export de Bytes utilisé par PdfWriter pour l'export pdf
	 * @param listeRecettes
	 * @throws Exception
	 */
	public void exportRecettePdf(OutputStream outputStream, Long id, Integer nb) 
			throws Exception  {
	
		Document document = new Document();
		PdfWriter.getInstance(document, outputStream);
				
		document.open();

			Recette recette = recetteService.getRecetteById(id);
			
			
			Font f=new Font(FontFamily.HELVETICA,25.0f,Font.UNDERLINE,BaseColor.BLACK);
			Paragraph paragraph1 = new Paragraph(recette.getNomRecette(),f);
			paragraph1.setSpacingAfter(30f);
			
		    document.add(paragraph1);
		    
		    
			PdfPCell cell;
			PdfPTable table = new PdfPTable(7);
			
			cell = new PdfPCell(new Phrase("Composition de la recette : " + recette.getNomRecette() + "(pour une portion)."));
		    cell.setColspan(7);
			cell.setHorizontalAlignment(1);
			cell.setBackgroundColor(BaseColor.GREEN);
		    table.addCell(cell);
		    
		    String[] entetes = {"Ingredients", "Quantite", "Sodium", "Fer", "Vitamine C", "Vitamine D", "Vitamine B12"};
		    
		    for(String entete : entetes) {
		    	cell = new PdfPCell(new Phrase(entete));
		    	cell.setBackgroundColor(BaseColor.CYAN);
			    table.addCell(cell);
		    }
		   
			for(RecetteIngredient ingredient : recette.getRecetteIngredients()) {
			
				// Calcul des nutriments par ingredient
				Double sodiumParIngredient = methodesPratiquesRepository.deuxChiffresSignificatifs
						(ingredient.getIngredients().getSodium()
						*ingredient.getQuantite()/1000);
				
				Double ferParIngredient = methodesPratiquesRepository.deuxChiffresSignificatifs
						(ingredient.getIngredients().getFer()
						*ingredient.getQuantite()/1000);
				
				Double vitamineCParIngredient = methodesPratiquesRepository.deuxChiffresSignificatifs
						(ingredient.getIngredients().getVitamineC()
						*ingredient.getQuantite()/1000);
				
				Double vitamineDParIngredient = methodesPratiquesRepository.deuxChiffresSignificatifs
						(ingredient.getIngredients().getVitamineD()
						*ingredient.getQuantite()/1000);
				
				Double vitamineB12ParIngredient = methodesPratiquesRepository.deuxChiffresSignificatifs
						(ingredient.getIngredients().getVitamineB12()
						*ingredient.getQuantite()/1000);
				
				
				table.addCell(ingredient.getIngredients().getLibelle());
				
				if(UNITE.equals(ingredient.getIngredients().getUniteMesure().getLabel())) {
					table.addCell(ingredient.getQuantite().toString());
				}else {
					
					table.addCell(ingredient.getQuantite().toString()
							+ " " + ingredient.getIngredients().getUniteMesure().getLabel());
				}
				
				table.addCell(sodiumParIngredient.toString() + " g ");
				table.addCell(ferParIngredient.toString() + " g " );
				table.addCell(vitamineCParIngredient.toString() + " g " );
				table.addCell(vitamineDParIngredient.toString() + " g " );
				table.addCell(vitamineB12ParIngredient.toString() + " g " );
			}
			
			// Appel de la méthode de calcul de nutriments par recette
			List<Double> apports = recetteService.calculNutrimentsParRecette_So_Fe_VitC_VitD_VitB12(recette);

			table.addCell("Total de la recette");
			table.addCell("");
			table.addCell(methodesPratiquesRepository.deuxChiffresSignificatifs(apports.get(0)/1000d)
					.toString() + " g ");
			table.addCell(methodesPratiquesRepository.deuxChiffresSignificatifs(apports.get(1)/1000d)
					.toString() + " g ");
			table.addCell(methodesPratiquesRepository.deuxChiffresSignificatifs(apports.get(2)/1000d)
					.toString() + " g ");
			table.addCell(methodesPratiquesRepository.deuxChiffresSignificatifs(apports.get(3)/1000d)
					.toString() + " g ");
			table.addCell(methodesPratiquesRepository.deuxChiffresSignificatifs(apports.get(4)/1000d)
					.toString() + " g ");
			table.setSpacingAfter(50);
			
			document.add(table);
			
			// Instructions
			f=new Font(FontFamily.HELVETICA,20.0f,Font.NORMAL,BaseColor.BLACK);
			paragraph1 = new Paragraph("Instructions : ",f);
			paragraph1.setSpacingAfter(20f);
			document.add(paragraph1);
			
			Set<Instruction> setInstructions = recette.getInstructions();
			
			Integer i = 1;
			for (Instruction instru : setInstructions) {
				document.add(new Paragraph( i + ". " + instru.getLibelle()));
				i++;
			}
			
			// Liste des ingrédients
			paragraph1 = new Paragraph("Ingrédients pour " + nb + " personnes : ",f);
			paragraph1.setSpacingAfter(20f);
			paragraph1.setSpacingBefore(20f);
			document.add(paragraph1);
			
			
		for (RecetteIngredient ri : recette.getRecetteIngredients()) {
			
			String unite = ri.getIngredients().getUniteMesure().getLabel();
			Double quantité = methodesPratiquesRepository.deuxChiffresSignificatifs(nb * ri.getQuantite());
			document.add(new Paragraph(ri.getIngredients().getLibelle() + " : " + quantité.toString() + " " + unite + " "));
			
		}
		document.close();
	}
		
	/**
	 * Export PDF du bilan de la semaine
	 * 
	 * @param outputStream
	 * 			Export de Bytes utilisé par PdfWriter pour l'export pdf
	 * @param listeRecettes
	 * 			Liste de recettes au format JSON
	 * @throws Exception
	 * 			On throws une exception
	 */
	public void exportBilanPdf(OutputStream outputStream, List<Recette> listeRecettes) 
			throws Exception  {
		
		Document document = new Document();
		PdfWriter.getInstance(document, outputStream);
				
		document.open();

		Double totalVitamineC = 0d;
		Double totalVitamineD = 0d;
		Double totalVitamineB12 = 0d;
		Double totalFer = 0d;
		Double totalSodium = 0d;
		
		for (Recette recette : listeRecettes) {
			recette = recetteService.getRecetteById(recette.getIdRecette());
			
			// Liste des apports
			List<Double> apports = recetteService.calculNutrimentsParRecette_So_Fe_VitC_VitD_VitB12(recette);
			
			totalSodium += apports.get(0);
			totalFer += apports.get(1);
			totalVitamineC += apports.get(2);
			totalVitamineD += apports.get(3);
			totalVitamineB12 += apports.get(4);
			
			PdfPCell cell;
			PdfPTable table = new PdfPTable(7);
			
			cell = new PdfPCell(new Phrase("Composition de la recette : " + recette.getNomRecette()));
		    cell.setColspan(7);
			cell.setHorizontalAlignment(1);
			cell.setBackgroundColor(BaseColor.GREEN);
		    table.addCell(cell);
		    
		    String[] entetes = {"Ingredients", "Quantite", "Sodium", "Fer", "Vitamine C", "Vitamine D", "Vitamine B12"};
		    
		    for(String entete : entetes) {
		    	cell = new PdfPCell(new Phrase(entete));
		    	cell.setBackgroundColor(BaseColor.CYAN);
			    table.addCell(cell);
		    }
		    
		    // Nutriments par ingredient
			for(RecetteIngredient ingredient : recette.getRecetteIngredients()) {
				
				table.addCell(ingredient.getIngredients().getLibelle());
				
				if(UNITE.equals(ingredient.getIngredients().getUniteMesure().getLabel())) {
					table.addCell(ingredient.getQuantite().toString());
				}else {
					table.addCell(ingredient.getQuantite().toString()
							+ " " + ingredient.getIngredients().getUniteMesure().getLabel());
				}
				
				Double sodiumParIngredient = methodesPratiquesRepository.deuxChiffresSignificatifs
						(ingredient.getIngredients().getSodium()
						*ingredient.getQuantite()/1000);
				
				Double ferParIngredient = methodesPratiquesRepository.deuxChiffresSignificatifs
						(ingredient.getIngredients().getFer()
						*ingredient.getQuantite()/1000);
				
				Double vitamineCParIngredient = methodesPratiquesRepository.deuxChiffresSignificatifs
						(ingredient.getIngredients().getVitamineC()
						*ingredient.getQuantite()/1000);
				
				Double vitamineDParIngredient = methodesPratiquesRepository.deuxChiffresSignificatifs
						(ingredient.getIngredients().getVitamineD()
						*ingredient.getQuantite()/1000);
				
				Double vitamineB12ParIngredient = methodesPratiquesRepository.deuxChiffresSignificatifs
						(ingredient.getIngredients().getVitamineB12()
						*ingredient.getQuantite()/1000);
				
				table.addCell(methodesPratiquesRepository.
						deuxChiffresSignificatifs(sodiumParIngredient) + " g ");
				table.addCell(methodesPratiquesRepository.
						deuxChiffresSignificatifs(ferParIngredient) + " g ");
				table.addCell(methodesPratiquesRepository.
						deuxChiffresSignificatifs(vitamineCParIngredient) + " g ");
				table.addCell(methodesPratiquesRepository.
						deuxChiffresSignificatifs(vitamineDParIngredient) + " g ");
				table.addCell(methodesPratiquesRepository.
						deuxChiffresSignificatifs(vitamineB12ParIngredient) + " g ");
			}
			
			// Total de la recette
			table.addCell("Total de la recette");
			table.addCell("");
			table.addCell(methodesPratiquesRepository.
					deuxChiffresSignificatifs(apports.get(0)/1000d).toString() + " g ");
			table.addCell(methodesPratiquesRepository.
					deuxChiffresSignificatifs(apports.get(1)/1000d).toString() + " g ");
			table.addCell(methodesPratiquesRepository.
					deuxChiffresSignificatifs(apports.get(2)/1000d).toString() + " g ");
			table.addCell(methodesPratiquesRepository.
					deuxChiffresSignificatifs(apports.get(3)/1000d).toString() + " g ");
			table.addCell(methodesPratiquesRepository.
					deuxChiffresSignificatifs(apports.get(4)/1000d).toString() + " g ");
			table.setSpacingAfter(50);
			
			document.add(table);
		}
		
		// Bilan en nutriments de la semaine
		document.add(new Paragraph("Le bilan en Sodium est de " + methodesPratiquesRepository.
				deuxChiffresSignificatifs(totalSodium/1000) + " g " ));	
		document.add(new Paragraph("Le bilan en Fer est de " + methodesPratiquesRepository.
				deuxChiffresSignificatifs(totalFer/1000) + " g "));
		document.add(new Paragraph("Le bilan en Vitamine C est de " + methodesPratiquesRepository.
				deuxChiffresSignificatifs(totalVitamineC/1000) + " g "));
		document.add(new Paragraph("Le bilan en Vitamine D est de " + methodesPratiquesRepository.
				deuxChiffresSignificatifs(totalVitamineD/1000) + " g "));
		document.add(new Paragraph("Le bilan en Vitamine B12 est de " + methodesPratiquesRepository.
				deuxChiffresSignificatifs(totalVitamineB12/1000) + " g "));
		document.close();
	}
	
	
	
	
}

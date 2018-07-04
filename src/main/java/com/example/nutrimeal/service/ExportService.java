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

		
		Double totalVitamines = 0d;
		Double totalMineraux = 0d;
		
			Recette recette = recetteService.getRecetteById(id);
			totalVitamines += bilanService.calculVitaminesPourRecette(recette);
			totalMineraux += bilanService.calculMinerauxPourRecette(recette);
			
			
			Font f=new Font(FontFamily.HELVETICA,30.0f,Font.UNDERLINE,BaseColor.BLACK);
			Paragraph paragraph1 = new Paragraph("Détail de la recette : " + recette.getNomRecette() + " pour " + 
			nb + " personnes.",f);
			paragraph1.setSpacingAfter(30f);
			
		    document.add(paragraph1);
		    
		    
			PdfPCell cell;
			PdfPTable table = new PdfPTable(4);
			
			cell = new PdfPCell(new Phrase("Composition de la recette : " + recette.getNomRecette() + "(pour une portion)."));
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
			
				Double vitaminesParIngredient = methodesPratiquesRepository.deuxChiffresSignificatifs
						(ingredient.getIngredients().getVitamines()
						*ingredient.getQuantite()/1000);
				
				Double minerauxParIngredient = methodesPratiquesRepository.deuxChiffresSignificatifs
						(ingredient.getIngredients().getMineraux()
						*ingredient.getQuantite()/1000);
				
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
		
					
			document.add(new Paragraph("Le bilan en vitamines est de " + methodesPratiquesRepository.
					deuxChiffresSignificatifs(totalVitamines/1000) + " g " ));	
			document.add(new Paragraph("Le bilan en mineraux est de " + methodesPratiquesRepository.
					deuxChiffresSignificatifs(totalMineraux/1000) + " g "));
			
			f=new Font(FontFamily.HELVETICA,20.0f,Font.NORMAL,BaseColor.BLACK);
			paragraph1 = new Paragraph("Instructions : ",f);
			paragraph1.setSpacingAfter(20f);
			document.add(paragraph1);
			
			Set<Instruction> setInstructions = recette.getInstructions();
			
			f=new Font(FontFamily.HELVETICA,20.0f,Font.NORMAL,BaseColor.BLACK);
			
			Integer i = 1;
			for (Instruction instru : setInstructions) {
				document.add(new Paragraph( i + ". " + instru.getLibelle(),f));
				i++;
			}
			
			
			paragraph1 = new Paragraph("Ingrédients : ",f);
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

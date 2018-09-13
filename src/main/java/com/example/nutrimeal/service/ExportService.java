package com.example.nutrimeal.service;


import java.io.OutputStream;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.nutrimeal.model.Instruction;
import com.example.nutrimeal.model.Recette;
import com.example.nutrimeal.model.RecetteIngredient;
import com.example.nutrimeal.repository.RecetteRepository;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import utils.Constantes;
import utils.MethodesPratiques;

@Service
public class ExportService {
	
	@Autowired 
	RecetteRepository recetteRepository;
	@Autowired
	BilanService bilanService;
	@Autowired
	RecetteService recetteService;
	@Autowired
	RecetteIngredientService recetteIngredientService;
	
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
	
		Document document = new Document(PageSize.A4);
		PdfWriter writer =  PdfWriter.getInstance(document, outputStream);	
		document.open();
		PdfContentByte canvas = writer.getDirectContentUnder();
		Image image = Image.getInstance(Constantes.ImagePdfBilan);
        image.scaleAbsolute(PageSize.A4.getWidth(), PageSize.A4.getHeight());
        image.setAbsolutePosition(0, 0);
        canvas.addImage(image);	
		
		
			// Reconstruction de l'objet recette
			Recette recette = recetteService.getRecetteById(id);
			
			// Nom de la recette
			Font f=new Font(FontFamily.HELVETICA,25.0f,Font.UNDERLINE,BaseColor.BLACK);
			Paragraph paragraph1 = new Paragraph(recette.getNomRecette(),f);
			paragraph1.setSpacingAfter(30f);
		    document.add(paragraph1);
		    
		    // Création du tableau
			PdfPCell cell;
			PdfPTable table = new PdfPTable(7);
			
			cell = new PdfPCell(new Phrase("Composition de la recette : " + recette.getNomRecette() + "(pour une portion)."));
		    cell.setColspan(7);
			cell.setHorizontalAlignment(1);
			cell.setBackgroundColor(BaseColor.GREEN);
		    table.addCell(cell);
		    
		    // Entêtes du tableau, seconde ligne
		    String[] entetes = {"Ingredients", "Quantite", "Sodium", "Fer", "Vitamine C", "Vitamine D", "Vitamine B12"};
		    
		    for(String entete : entetes) {
		    	cell = new PdfPCell(new Phrase(entete));
		    	cell.setBackgroundColor(BaseColor.CYAN);
			    table.addCell(cell);
		    }
		   
		    // Valeurs du tableau
			for(RecetteIngredient recetteIngredient : recette.getRecetteIngredients()) {
		
				// Nom de l'ingredient
				table.addCell(recetteIngredient.getIngredients().getLibelle());
				
				// Quantité d'ingredients
				table.addCell(recetteIngredientService.quantiteIngredients(recetteIngredient));
				
				// Calcul des nutriments par ingredient
				List<Double> nutrimentsParIngredients = 
						recetteIngredientService.nutrimentsParIngredients_So_Fe_VC_VD_VB12(recetteIngredient);
				
				table.addCell(MethodesPratiques.deuxChiffresSignificatifs(nutrimentsParIngredients.get(0)
						* 100d / Constantes.Sodium).toString() + " %");
				table.addCell(MethodesPratiques.deuxChiffresSignificatifs(nutrimentsParIngredients.get(1)
						* 100d / Constantes.Fer).toString() + " %");
				table.addCell(MethodesPratiques.deuxChiffresSignificatifs(nutrimentsParIngredients.get(2)
						* 100d / Constantes.VitamineC).toString() + " %");
				table.addCell(MethodesPratiques.deuxChiffresSignificatifs(nutrimentsParIngredients.get(3)
						* 100d / Constantes.VitamineD).toString() + " %");
				table.addCell(MethodesPratiques.deuxChiffresSignificatifs(nutrimentsParIngredients.get(4)
						* 100d / Constantes.VitamineB12).toString() + " %");
			}
			
			// Appel de la méthode de calcul de nutriments par recette
			List<Double> apports = recetteService.calculNutrimentsParRecette_So_Fe_VitC_VitD_VitB12(recette);

			table.addCell("Total de la recette");
			table.addCell("");
			table.addCell(MethodesPratiques.deuxChiffresSignificatifs(apports.get(0)
					* 100d / Constantes.Sodium).toString() + " %");
			table.addCell(MethodesPratiques.deuxChiffresSignificatifs(apports.get(1)
					* 100d / Constantes.Fer).toString() + " %");
			table.addCell(MethodesPratiques.deuxChiffresSignificatifs(apports.get(2)
					* 100d / Constantes.VitamineC).toString() + " %");
			table.addCell(MethodesPratiques.deuxChiffresSignificatifs(apports.get(3)
					* 100d / Constantes.VitamineD).toString() + " %");
			table.addCell(MethodesPratiques.deuxChiffresSignificatifs(apports.get(4)
					* 100d / Constantes.VitamineB12).toString() + " %");
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
			Double quantité = MethodesPratiques.deuxChiffresSignificatifs(nb * ri.getQuantite());
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
		
		Document document = new Document(PageSize.A4);
		PdfWriter writer =  PdfWriter.getInstance(document, outputStream);	
		document.open();
		PdfContentByte canvas = writer.getDirectContentUnder();		
		
		Paragraph paragraph1 = new Paragraph("Votre bilan : ");
		paragraph1.setSpacingAfter(50f);
	    document.add(paragraph1);
	    
		Double totalVitamineC = 0d;
		Double totalVitamineD = 0d;
		Double totalVitamineB12 = 0d;
		Double totalFer = 0d;
		Double totalSodium = 0d;
		
		for (Recette recette : listeRecettes) {
			
			// Reconstruction de l'objet recette
			recette = recetteService.getRecetteById(recette.getId());
			
			// Liste des apports
			List<Double> apports = recetteService.calculNutrimentsParRecette_So_Fe_VitC_VitD_VitB12(recette);
			
			totalSodium += apports.get(0);
			totalFer += apports.get(1);
			totalVitamineC += apports.get(2);
			totalVitamineD += apports.get(3);
			totalVitamineB12 += apports.get(4);
			
			// Création du tableau
			PdfPCell cell;
			PdfPTable table = new PdfPTable(7);
			
			cell = new PdfPCell(new Phrase("Composition de la recette : " + recette.getNomRecette()));
		    cell.setColspan(7);
			cell.setHorizontalAlignment(1);
			cell.setBackgroundColor(BaseColor.GREEN.brighter());
		    table.addCell(cell);
		    
		    // Création d'entête
		    String[] entetes = {"Ingredients", "Quantite", "Sodium", "Fer", "Vitamine C", "Vitamine D", "Vitamine B12"};
		    for(String entete : entetes) 
		    {
		    	cell = new PdfPCell(new Phrase(entete));
		    	cell.setBackgroundColor(BaseColor.CYAN.brighter());
			    table.addCell(cell);
		    }
		    
		    // Mise dans le tableau Nutriments par ingredient
			for(RecetteIngredient recetteIngredient : recette.getRecetteIngredients()) 
			{
				table.addCell(recetteIngredient.getIngredients().getLibelle());
				table.addCell(recetteIngredientService.quantiteIngredients(recetteIngredient));
				
				List<Double> nutrimentsParIngredients = 
						recetteIngredientService.nutrimentsParIngredients_So_Fe_VC_VD_VB12(recetteIngredient);
				
				table.addCell(MethodesPratiques.deuxChiffresSignificatifs(nutrimentsParIngredients.get(0) 
						* 100d / Constantes.Sodium).toString() + " %");
				table.addCell(MethodesPratiques.deuxChiffresSignificatifs(nutrimentsParIngredients.get(1) 
						* 100d / Constantes.Fer).toString() + " %");
				table.addCell(MethodesPratiques.deuxChiffresSignificatifs(nutrimentsParIngredients.get(2) 
						* 100d / Constantes.VitamineC).toString() + " %");
				table.addCell(MethodesPratiques.deuxChiffresSignificatifs(nutrimentsParIngredients.get(3) 
						* 100d / Constantes.VitamineD).toString() + " %");
				table.addCell(MethodesPratiques.deuxChiffresSignificatifs(nutrimentsParIngredients.get(4) 
						* 100d / Constantes.VitamineB12).toString() + " %");	
			}
			
			// Mise dans le tableau du total de la recette
			table.addCell("Total de la recette");
			table.addCell("");
			table.addCell(MethodesPratiques.
					deuxChiffresSignificatifs(apports.get(0) * 100d / Constantes.Sodium).toString() + " %");
			table.addCell(MethodesPratiques.
					deuxChiffresSignificatifs(apports.get(1) * 100d / Constantes.Fer).toString() + " %");
			table.addCell(MethodesPratiques.
					deuxChiffresSignificatifs(apports.get(2) * 100d / Constantes.VitamineC).toString() + " %");
			table.addCell(MethodesPratiques.
					deuxChiffresSignificatifs(apports.get(3) * 100d / Constantes.VitamineD).toString() + " %");
			table.addCell(MethodesPratiques.
					deuxChiffresSignificatifs(apports.get(4) * 100d / Constantes.VitamineB12).toString() + " %");
			table.setSpacingAfter(50);
			
			document.add(table);
		}
		
		// Bilan en nutriments de la semaine
		document.add(new Paragraph("Le bilan en Sodium est de " + MethodesPratiques.
				deuxChiffresSignificatifs(totalSodium) + " µg " ));	
		document.add(new Paragraph("Le bilan en Fer est de " + MethodesPratiques.
				deuxChiffresSignificatifs(totalFer) + " µg "));
		document.add(new Paragraph("Le bilan en Vitamine C est de " + MethodesPratiques.
				deuxChiffresSignificatifs(totalVitamineC) + " µg "));
		document.add(new Paragraph("Le bilan en Vitamine D est de " + MethodesPratiques.
				deuxChiffresSignificatifs(totalVitamineD) + " µg "));
		document.add(new Paragraph("Le bilan en Vitamine B12 est de " + MethodesPratiques.
				deuxChiffresSignificatifs(totalVitamineB12) + " µg "));
	
		
		Image image = Image.getInstance(Constantes.ImagePdfBilan);
        image.scaleAbsolute(PageSize.A4.getWidth(), PageSize.A4.getHeight());
        image.setAbsolutePosition(0, 0);
        canvas.addImage(image);
        
        PdfGState gState = new PdfGState();
        gState.setStrokeOpacity(0.5f);
        canvas.setGState(gState);
		
        document.close();
		
	}
}

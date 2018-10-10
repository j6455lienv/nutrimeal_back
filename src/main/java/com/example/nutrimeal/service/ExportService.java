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
import utils.HandleNumbers;

@Service
public class ExportService {

  protected final RecetteRepository recetteRepository;
  protected final BilanService bilanService;
  protected final RecetteService recetteService;
  protected final RecetteIngredientService recetteIngredientService;

  @Autowired
  public ExportService(RecetteRepository recetteRepository,
                       BilanService bilanService,
                       RecetteService recetteService,
                       RecetteIngredientService recetteIngredientService) {
    this.recetteRepository = recetteRepository;
    this.bilanService = bilanService;
    this.recetteService = recetteService;
    this.recetteIngredientService = recetteIngredientService;
  }

  /**
   * Export PDF de la recette sélectionnée
   *
   * @param outputStream Export de Bytes utilisé par PdfWriter pour l'export pdf
   * @param id           id recette
   * @param nb           number of persons
   * @throws Exception exception
   */
  public void exportRecettePdf(OutputStream outputStream, Long id, Integer nb)
      throws Exception {

    Document document = new Document(PageSize.A4);
    PdfWriter writer = PdfWriter.getInstance(document, outputStream);
    document.open();
    PdfContentByte canvas = writer.getDirectContentUnder();
    Image image = Image.getInstance(Constantes.IMAGES_PDF_BILAN);
    image.scaleAbsolute(PageSize.A4.getWidth(), PageSize.A4.getHeight());
    image.setAbsolutePosition(0, 0);
    canvas.addImage(image);

    // Reconstruction de l'objet recette
    Recette recette = recetteService.getRecetteById(id);

    // Nom de la recette
    Font f = new Font(FontFamily.HELVETICA, 25.0f, Font.UNDERLINE, BaseColor.BLACK);
    Paragraph paragraph1 = new Paragraph(recette.getNomRecette(), f);
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

    for (String entete : entetes) {
      cell = new PdfPCell(new Phrase(entete));
      cell.setBackgroundColor(BaseColor.CYAN);
      table.addCell(cell);
    }

    createTable(recette, table);

    // Appel de la méthode de calcul de nutriments par recette
    List<Double> apports = recetteService.calculNutrimentsParRecette(recette);

    table.addCell("Total de la recette");
    table.addCell("");
    table.addCell(HandleNumbers.get(apports.get(0) * 100d / Constantes.SODIUM).toString() + " %");
    table.addCell(HandleNumbers.get(apports.get(1) * 100d / Constantes.FER).toString() + " %");
    table.addCell(HandleNumbers.get(apports.get(2) * 100d / Constantes.VITAMINE_C).toString() + " %");
    table.addCell(HandleNumbers.get(apports.get(3) * 100d / Constantes.VITAMINE_D).toString() + " %");
    table.addCell(HandleNumbers.get(apports.get(4) * 100d / Constantes.VITAMINE_B12).toString() + " %");
    table.setSpacingAfter(50);

    document.add(table);

    // Instructions
    f = new Font(FontFamily.HELVETICA, 20.0f, Font.NORMAL, BaseColor.BLACK);
    paragraph1 = new Paragraph("Instructions : ", f);
    paragraph1.setSpacingAfter(20f);
    document.add(paragraph1);

    Set<Instruction> setInstructions = recette.getInstructions();

    int i = 1;
    for (Instruction instru : setInstructions) {
      document.add(new Paragraph(i + ". " + instru.getLibelle()));
      i++;
    }

    // Liste des ingrédients
    paragraph1 = new Paragraph("Ingrédients pour " + nb + " personnes : ", f);
    paragraph1.setSpacingAfter(20f);
    paragraph1.setSpacingBefore(20f);
    document.add(paragraph1);

    for (RecetteIngredient ri : recette.getRecetteIngredients()) {

      String unite = ri.getIngredients().getUniteMesure().getLabel();
      Double quantite = HandleNumbers.get(nb * ri.getQuantite());
      document.add(new Paragraph(ri.getIngredients().getLibelle() + " : " + quantite.toString() + " " + unite + " "));
    }

    document.close();
  }

  /**
   * Export PDF du bilan de la semaine
   *
   * @param outputStream  Export de Bytes utilisé par PdfWriter pour l'export pdf
   * @param listeRecettes Liste de recettes au format JSON
   * @throws Exception On throws une exception
   */
  public void exportBilanPdf(OutputStream outputStream, List<Recette> listeRecettes) throws Exception {

    Document document = new Document(PageSize.A4);
    PdfWriter writer = PdfWriter.getInstance(document, outputStream);
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
      List<Double> apports = recetteService.calculNutrimentsParRecette(recette);

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
      for (String entete : entetes) {
        cell = new PdfPCell(new Phrase(entete));
        cell.setBackgroundColor(BaseColor.CYAN.brighter());
        table.addCell(cell);
      }

      //create Table
      createTable(recette, table);

      // Mise dans le tableau du total de la recette
      table.addCell("Total de la recette");
      table.addCell("");
      table.addCell(HandleNumbers.get(apports.get(0) * 100d / Constantes.SODIUM).toString() + " %");
      table.addCell(HandleNumbers.get(apports.get(1) * 100d / Constantes.FER).toString() + " %");
      table.addCell(HandleNumbers.get(apports.get(2) * 100d / Constantes.VITAMINE_C).toString() + " %");
      table.addCell(HandleNumbers.get(apports.get(3) * 100d / Constantes.VITAMINE_D).toString() + " %");
      table.addCell(HandleNumbers.get(apports.get(4) * 100d / Constantes.VITAMINE_B12).toString() + " %");
      table.setSpacingAfter(50);

      document.add(table);
    }

    // Bilan en nutriments de la semaine
    document.add(new Paragraph("Le bilan en Sodium est de " + HandleNumbers.get(totalSodium) + " µg "));
    document.add(new Paragraph("Le bilan en Fer est de " + HandleNumbers.get(totalFer) + " µg "));
    document.add(new Paragraph("Le bilan en Vitamine C est de " + HandleNumbers.get(totalVitamineC) + " µg "));
    document.add(new Paragraph("Le bilan en Vitamine D est de " + HandleNumbers.get(totalVitamineD) + " µg "));
    document.add(new Paragraph("Le bilan en Vitamine B12 est de " + HandleNumbers.get(totalVitamineB12) + " µg "));


    Image image = Image.getInstance(Constantes.IMAGES_PDF_BILAN);
    image.scaleAbsolute(PageSize.A4.getWidth(), PageSize.A4.getHeight());
    image.setAbsolutePosition(0, 0);
    canvas.addImage(image);

    PdfGState gState = new PdfGState();
    gState.setStrokeOpacity(0.5f);
    canvas.setGState(gState);

    document.close();

  }

  // delete duplicated code
  private void createTable(Recette recette, PdfPTable table) {
    // Mise dans le tableau Nutriments par ingredient
    for (RecetteIngredient recetteIngredient : recette.getRecetteIngredients()) {
      // Nom de l'ingredient
      table.addCell(recetteIngredient.getIngredients().getLibelle());
      // Quantité d'ingredients
      table.addCell(recetteIngredientService.quantiteIngredients(recetteIngredient));
      // Calcul des nutriments par ingredient
      List<Double> nutrimentsIngredients = recetteIngredientService.nutrimentsParIngredients(recetteIngredient);

      table.addCell(HandleNumbers.get(nutrimentsIngredients.get(0) * 100d / Constantes.SODIUM).toString() + " %");
      table.addCell(HandleNumbers.get(nutrimentsIngredients.get(1) * 100d / Constantes.FER).toString() + " %");
      table.addCell(HandleNumbers.get(nutrimentsIngredients.get(2) * 100d / Constantes.VITAMINE_C).toString() + " %");
      table.addCell(HandleNumbers.get(nutrimentsIngredients.get(3) * 100d / Constantes.VITAMINE_D).toString() + " %");
      table.addCell(HandleNumbers.get(nutrimentsIngredients.get(4) * 100d / Constantes.VITAMINE_B12).toString() + " %");
    }
  }
}

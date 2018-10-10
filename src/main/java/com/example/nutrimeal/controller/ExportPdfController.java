package com.example.nutrimeal.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.nutrimeal.model.Recette;
import com.example.nutrimeal.service.ExportService;

@RestController
public class ExportPdfController {

  private ExportService exportService;

  @Autowired
  private ExportPdfController(ExportService exportService) {
    this.exportService = exportService;
  }

  /**
   * Export list of
   *
   * @param listeRecettes List Recette
   * @param response      HttpServletResponse, callback response
   * @throws Exception ex
   */
  @RequestMapping("/bilan/pdf")
  public void exportBilanPDF(@RequestBody List<Recette> listeRecettes,
                             HttpServletResponse response) throws Exception {
    response.setContentType("application/pdf; charset=UTF-8");
    response.setHeader("Content-Disposition", "attachment; filename=BilanSemaine.pdf");
    exportService.exportBilanPdf(response.getOutputStream(), listeRecettes);
  }

  /**
   * Exporte en pdf la recette persistée demandée par une requête.
   *
   * @param id       Long ID of the recette
   * @param nb       Integer numbers of persons
   * @param response HttpServletResponse, callback response
   * @throws Exception ex
   */
  @GetMapping(path = "/recette/{id}/nbPersonnes/{nb}/pdf")
  public void exportPdfThisRecette(@PathVariable("id") Long id, @PathVariable("nb") Integer nb,
                                   HttpServletResponse response) throws Exception {
    response.setContentType("application/pdf; charset=UTF-8");
    response.setHeader("Content-Disposition", "attachment; filename=Recette.pdf");
    exportService.exportRecettePdf(response.getOutputStream(), id, nb);
  }


}

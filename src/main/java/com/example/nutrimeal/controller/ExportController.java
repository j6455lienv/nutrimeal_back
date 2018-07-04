package com.example.nutrimeal.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.nutrimeal.model.Recette;
import com.example.nutrimeal.service.ExportService;

/**
 * @author Gaetan Inidjel
 */
@RestController
public class ExportController {

    @Autowired
    ExportService exportService;

    /**
     * Méthode qui exporte en pdf la liste des recettes, minéraux, vitamines et ingredients
     *
     * @param listeIdAsString Liste d'id en chaine de caractères (ex : "1,2") séparés par des virgules
     * @param request         Requete HTTP
     * @param response        Réponse du server HTTP
     * @throws Exception On throws une exception
     */
    @RequestMapping("/bilan/pdf")
    public void exportBilanPDF(@RequestBody List<Recette> listeRecettes, HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=BilanSemaine.pdf");
        exportService.exportBilanPdf(response.getOutputStream(), listeRecettes);
    }
    
	/** Exporte en pdf la recette persistée demandée par une requête.
	 * @param id l'id de la recette
	 * @return Un document pdf avec la recette serialisée
	 * @throws Exception 
	 * @throws IOException */
	@RequestMapping(value = "/recette/{id}/nbPersonnes/{nb}/pdf", method = RequestMethod.GET)
	public void exportPdfThisRecette(@PathVariable("id") Long id, @PathVariable("nb") Integer nb, HttpServletRequest request,
            HttpServletResponse response) throws IOException, Exception {
		response.setContentType("application/pdf; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=Recette.pdf");
        exportService.exportRecettePdf(response.getOutputStream(), id, nb);
	}


}

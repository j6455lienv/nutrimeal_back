package com.example.nutrimeal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.nutrimeal.model.Recette;
import com.example.nutrimeal.service.ExportService;

/**
 * 
 * @author Gaetan Inidjel
 *
 */
@RestController
public class ExportController {

	@Autowired
	ExportService exportBilanPdfService;
	
	/**
	 * Méthode qui exporte en pdf la liste des recettes, minéraux, vitamines et ingredients
	 * 
	 * @param listeIdAsString
	 * 			Liste d'id en chaine de caractères (ex : "1,2") séparés par des virgules
	 * @param request
	 * 			Requete HTTP
	 * @param response
	 * 			Réponse du server HTTP
	 * @throws Exception
	 * 			On throws une exception
	 */
	@RequestMapping("/bilan/pdf")
	    public void exportBilanPDF(@RequestBody List<Recette> listeRecettes, HttpServletRequest request, 
	    		HttpServletResponse response) throws Exception {
	        response.setContentType("application/pdf");
	        response.setHeader("Content-Disposition", "attachment; filename=\"Bilan de la semaine.pdf\"");
	        exportBilanPdfService.exportBilanPdfService(response.getOutputStream(), listeRecettes);	        
	    }
	
	
}

package com.example.nutrimeal.service;


import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.nutrimeal.model.Paire;
import com.example.nutrimeal.model.Recette;
import com.example.nutrimeal.repository.MethodesPratiquesRepository;
import com.example.nutrimeal.repository.RecetteRepository;


/**
 * 
 * @author Gaetan Inidjel
 *
 */
@Service
public class RecetteService {

	/**
	 * Nom de la BDD
	 */
	public final static String database = "jdbc:h2:mem:burnTheHeretic";
	
	/**
	 * User
	 */
	public final static String userName = "sa";
	
	/**
	 * mdp
	 */
	public final static String password = "";
	
	/**
	 * Requête SQL pour alimentation des listes
	 */
	public final static String listesFront = "select ID_RECETTE, NOM_RECETTE from RECETTE";
	
	@Autowired
	MethodesPratiquesRepository methodesPratiquesRepository;
	@Autowired 
	private RecetteRepository recetteRepository;
		
	/**
	 * Méthode qui retourne une recette lorsque l'on donne une idRecette
	 * 
	 * @param id
	 * 			id de la recette à récupérer
	 * @return
	 * 			Un objet Recette
	 */
	public Recette getRecetteById (Long id){
		return recetteRepository.getOne(id);
	}
	
	
	/**
	 * Méthode qui extrait la liste des recettes au format clé / valeur
	 * 
	 * @return
	 * 		Liste<Paire> utilisée pour alimenter les listes de recettes
	 * @throws SQLException
	 */
    public List<Paire> alimentationListesRecettes() throws SQLException{
	
    // Connection à la database et requête sur NOM_RECETTE et ID_RECETTE
    Statement stmt = null;
	stmt = DriverManager.getConnection(database, userName, password).createStatement();
	ResultSet rs =  stmt.executeQuery(listesFront);
		
	List<Paire> listePaireIdRecetteNomRecette = new ArrayList<>();
		while(rs.next()) {
			String value = rs.getString("NOM_RECETTE");
			Long key = rs.getLong("ID_RECETTE");
			
		listePaireIdRecetteNomRecette.add(methodesPratiquesRepository.creationPaireKeyValue(key, value));
		}
	return listePaireIdRecetteNomRecette;
	}
}

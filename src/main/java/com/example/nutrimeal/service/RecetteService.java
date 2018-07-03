package com.example.nutrimeal.service;


import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
	
	/** Nombre de recettes aléatoirement tirées de la base pour le carroussel.*/
	private static final int CARROUSSEL_RECETTE_NUMBER = 10;

	/**
	 * Nom de la BDD
	 */
	public final static String database = "jdbc:h2:mem:test";
	
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
    public Map<Long, String> alimentationListesRecettes() throws SQLException{
	
    // Connection à la database et requête sur NOM_RECETTE et ID_RECETTE
    Statement stmt = null;
	stmt = DriverManager.getConnection(database, userName, password).createStatement();
	ResultSet rs =  stmt.executeQuery(listesFront);
		
	Map<Long, String> listePaireIdRecetteNomRecette = new HashMap<>();
		while(rs.next()) {
			String value = rs.getString("NOM_RECETTE");
			Long key = rs.getLong("ID_RECETTE");
			
		listePaireIdRecetteNomRecette.put(key, value);
		}
	return listePaireIdRecetteNomRecette;
	
    }

	/** Récupère une recette persistée à partir de son id.
	 * @param id l'id de la recette
	 * @return la recette persistée */
	public Optional<Recette> get(Long id) {
		return recetteRepository.findById(id);
	}

	/** Récupère une page de résultat de recette contenant la chaine recherchée.
	 * @param string la chaine recherchée
	 * @param pageable les paramètres de pagination
	 * @return la liste des recettes correspondant à la recherche*/
	public Page<Recette> findRecetteContaining(String string, Pageable pageable) {
		return recetteRepository.findByNomRecetteContainsIgnoreCaseOrderByNomRecette(pageable, string);
	}

	/**  Récupère aléatoirement des recettes avec images en base pour le carroussel de la page d'accueil.
	 * @return la liste des recettes pris aléatoirement en base*/
	public Set<Recette> findRandomRecettesWithImages() {
		int elementsPerPage = 20;
		Set<Recette> result = new HashSet<>();

		Page<Recette> recetteListe = recetteRepository.findByBase64ImageCodeNotNull(PageRequest.of(0, elementsPerPage));
		
		if (recetteListe.hasContent()) {
			long totalElementsNumber = recetteListe.getTotalElements();
			int i = 0;
			int randomRecetteIndex;
			int askedPage;
			int elementIndexInPage;
			List<Recette> pageContent;
			
			while (i < CARROUSSEL_RECETTE_NUMBER) {
				randomRecetteIndex = (int) (Math.floor(Math.random() * totalElementsNumber));
				askedPage = (int) (Math.floor(randomRecetteIndex / elementsPerPage));
				
				recetteListe = recetteRepository.findByBase64ImageCodeNotNull(PageRequest.of(askedPage, elementsPerPage));
				
				pageContent = recetteListe.getContent();
				elementIndexInPage = randomRecetteIndex % elementsPerPage;
				
				result.add(pageContent.get(elementIndexInPage));
				i++;
			}
		}
		return result;
	}
}

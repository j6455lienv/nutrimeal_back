package com.example.nutrimeal.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.nutrimeal.model.Recette;

@Repository
public interface RecetteRepository extends PagingAndSortingRepository<Recette, Long>{
	
	/** Récupère une page d'élément de la base contenant une chaine de caractère.
	 * @param Pageable pageable les paramètres de pagination
	 * @param string la chaine de caractère
	 * @return la page demandée */
	Page<Recette> findByNomContainsIgnoreCaseOrderByNom(Pageable pageable, String string);

	/**
	 * Récupère une page de recette ayant des images en base. 
	 * @param pageable paramètres de pagination
	 * @return une page de recette en base avec des images non nulles
	 */
	Page<Recette> findByBase64ImageCodeNotNull(Pageable pageable);
}

package com.example.nutrimeal.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;


@Entity(name = "RECETTE")
public class Recette {

	@Getter
	@Setter
	@Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_RECETTE")
    private Long idRecette;	

	@Getter
	@Setter
    @OneToMany(mappedBy="recette")
    private Set<RecetteIngredient> recetteIngredients = new HashSet<>();

	@Getter
	@Setter
	@Column(name = "NOM_RECETTE")
	private String nomRecette;
	
	@Getter
	@Setter
	@Column(name = "TEMPS_PREPARATION")
	private Integer tempsPreparation;
	
	@Getter
	@Setter
	private Double minerauxParPortion;
	
	@Getter
	@Setter
	private Double vitaminesParPortion;
	
}

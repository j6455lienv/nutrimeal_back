package com.example.nutrimeal.model;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;


@Entity(name = "RECETTE")
@Getter
@Setter
public class Recette {

	@Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_RECETTE")
    private Long idRecette;	

    @OneToMany(mappedBy="idRecette")
    private Set<RecetteIngredient> recetteIngredients = new HashSet<>();

	@Column(name = "NOM_RECETTE")
	private String nomRecette;
	
	@Column(name = "TEMPS_PREPARATION")
	private Integer tempsPreparation;
	
	@Transient
	private Double minerauxParPortion;
	
	@Transient
	private Double vitaminesParPortion;
	
	@Column(name = "image_recette")
	private String base64ImageCode;	
	
	@OneToMany(mappedBy="recetteId")
    @OrderBy(value = "chrono")
    private Set<Instruction> instructions = new LinkedHashSet<>();
	
}

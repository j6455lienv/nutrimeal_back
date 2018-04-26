package com.example.nutrimeal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Recette {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_recette")
    private Long id;
	
	@Column(name = "nom_recette")
	private String nom;
	
	@Column(name = "min_par_por")
	private Double minerauxParPortion;
	
	@Column(name = "vit_par_por")
	private Double vitaminesParPortion;
	
	@Column(name = "temps_prepa" )
	private Integer tempsPreparation;
}

package com.example.nutrimeal.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "recette_ingredient")
@Getter
@Setter
public class RecetteIngredient implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_recette")
	private Long idRecette;
	
	@Id
	@Column(name = "id_ingredient")
	private Long idIngredient;
	
	@Column
	private Double quantite;
}

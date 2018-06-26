package com.example.nutrimeal.model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;


@IdClass(value = RecetteIngredient.class)
@Entity(name = "RECETTE_INGREDIENT")
public class RecetteIngredient implements Serializable{



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@Id
	@Column(name = "ID_RECETTE")
	private Long idRecette;
	
	@Getter
	@Setter
	@Id
	@Column(name = "ID_INGREDIENT")
	private Long idIngredient;
	
	@Getter
	@Setter
	@ManyToOne
    @JoinColumn(name="ID_RECETTE", nullable=false, insertable= false, updatable=false)
    public Recette recette;
	
	@Getter
	@Setter
	@Column(name = "QUANTITE")
	private Double quantite;

	@Getter
	@Setter
	@ManyToOne
    @JoinColumn(name="ID_INGREDIENT", nullable=false, insertable= false, updatable=false)
    public Ingredient ingredients;
}

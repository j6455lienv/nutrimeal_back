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


@Entity(name = "INGREDIENT")
public class Ingredient {
	
	@Getter
	@Setter
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_INGREDIENT")
	private Long idIngredient;
	
	@Getter
	@Setter
	@OneToMany(mappedBy="ingredients")
	public Set<RecetteIngredient> listeRecettes = new HashSet<>();

	@Getter
	@Setter
	@Column
	private String libelle;
	
	@Getter
	@Setter
	@Column
	private Double quantite;

	@Getter
	@Setter
	@Column(name = "UNITE_MESURE")
	private String uniteMesure;
	
	@Getter
	@Setter
	@Column
	private Double vitamines;
	
	@Getter
	@Setter
	@Column
	private Double mineraux;

}

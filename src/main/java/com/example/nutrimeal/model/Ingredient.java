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
@Getter
@Setter
public class Ingredient {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_INGREDIENT")
	private Long idIngredient;
	
	@OneToMany(mappedBy="ingredients")
	public Set<RecetteIngredient> listeRecettes = new HashSet<>();

	@Column
	private String libelle;

	@Column(name = "UNITE_MESURE")
	private String uniteMesure;
	
	@Column
	private Double vitamines;
	
	@Column
	private Double mineraux;
}
